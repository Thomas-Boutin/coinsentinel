package fr.ippon.androidaacsample.coinsentinel.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.repository.CoinRepository
import fr.ippon.androidaacsample.coinsentinel.repository.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModel @Inject constructor(
    private val coinRepository: CoinRepository
): ViewModel() {
    var coins: LiveData<Resource<Array<Coin>>> = coinRepository.coins

    fun fetchCoins() {
        coinRepository.fetchCoins()
    }
}