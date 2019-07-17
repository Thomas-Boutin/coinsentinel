package fr.ippon.androidaacsample.coinsentinel.di

import androidx.room.Room
import fr.ippon.androidaacsample.coinsentinel.db.AppDatabase
import org.koin.dsl.module

private const val DATABASE_NAME = "COIN_DB"

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}