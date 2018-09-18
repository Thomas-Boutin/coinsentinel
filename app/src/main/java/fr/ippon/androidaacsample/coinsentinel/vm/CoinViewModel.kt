package fr.ippon.androidaacsample.coinsentinel.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import awaitObjectResult
import com.github.kittinunf.fuel.Fuel
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinRouting
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.util.Resource
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModel @Inject constructor(
    private val coinResultDeserializer: CoinResultDeserializer
): ViewModel() {
    val coins: MutableLiveData<Resource<Array<Coin>>> = MutableLiveData()

    suspend private fun getCoins() = GlobalScope.produce(Dispatchers.Default, 0) {
        val emptyData: Array<Coin> = emptyArray()
        send(Resource.loading(emptyData))

        Fuel.request(CoinRouting.GetCoins())
            .awaitObjectResult(coinResultDeserializer)
            .fold(success = { response ->
                send(Resource.success(response.data))
            }, failure = { error ->
                send(Resource.error(error, emptyData))
            })
    }

    fun fetchCoins() = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
        getCoins().consumeEach { it ->
            coins.value = it
        }
    }
}