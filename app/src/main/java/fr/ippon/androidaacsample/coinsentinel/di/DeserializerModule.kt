package fr.ippon.androidaacsample.coinsentinel.di

import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import org.koin.dsl.module

val deserializerModule = module {
    single { CoinResultDeserializer(get()) }
}