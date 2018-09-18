package fr.ippon.androidaacsample.coinsentinel.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import awaitObjectResult
import com.github.kittinunf.fuel.Fuel
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinRouting
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.db.CoinDao
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepository @Inject constructor(
        private val coinDao: CoinDao,
        private val coinResultDeserializer: CoinResultDeserializer
) {
    private val _coins = MediatorLiveData<Resource<Array<Coin>>>()
    val coins: LiveData<Resource<Array<Coin>>>
        get() {
            return _coins
        }

    init {
        subscribeToDatabase()
    }

    private fun subscribeToDatabase() {
        val sourceDb = coinDao.getAll()
        _coins.value = Resource.loading(emptyArray())

        _coins.addSource(sourceDb) {
            _coins.value = Resource.success(it)
        }
    }

    private suspend fun getCoins() = GlobalScope.produce(Dispatchers.Default, 0) {
        val lastData: Array<Coin> = coins.value?.data ?: emptyArray()
        send(Resource.loading(lastData))

        Fuel.request(CoinRouting.GetCoins())
                .awaitObjectResult(coinResultDeserializer)
                .fold(success = { response ->
                    coinDao.insertAll(response.data)
                }, failure = { error ->
                    send(Resource.error(error, lastData))
                })
    }

    fun fetchCoins() = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
        getCoins().consumeEach { it ->
            _coins.value = it
        }
    }
}