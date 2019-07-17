package fr.ippon.androidaacsample.coinsentinel.di

import fr.ippon.androidaacsample.coinsentinel.repository.CoinRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CoinRepository(get(), get()) }
}