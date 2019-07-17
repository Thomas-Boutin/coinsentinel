package fr.ippon.androidaacsample.coinsentinel.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.repository.CoinRepository
import fr.ippon.androidaacsample.coinsentinel.repository.Resource

class CoinViewModel constructor(
    private val coinRepository: CoinRepository
): ViewModel() {
    val coins: LiveData<Resource<Array<Coin>>> = coinRepository.coins

    fun fetchCoins() {
        coinRepository.fetchCoins()
    }
}