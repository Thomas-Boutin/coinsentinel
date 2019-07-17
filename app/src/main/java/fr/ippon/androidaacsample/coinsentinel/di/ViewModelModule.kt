package fr.ippon.androidaacsample.coinsentinel.di

import fr.ippon.androidaacsample.coinsentinel.vm.CoinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CoinViewModel(get()) }
}