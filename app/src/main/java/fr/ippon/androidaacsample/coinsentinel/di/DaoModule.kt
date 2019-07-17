package fr.ippon.androidaacsample.coinsentinel.di

import fr.ippon.androidaacsample.coinsentinel.db.AppDatabase
import org.koin.dsl.module

val daoModule = module {
    single {
        get<AppDatabase>().coinDao()
    }
}