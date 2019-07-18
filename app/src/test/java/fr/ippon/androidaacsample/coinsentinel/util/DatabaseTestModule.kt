package fr.ippon.androidaacsample.coinsentinel.util

import androidx.room.Room
import fr.ippon.androidaacsample.coinsentinel.db.AppDatabase
import org.koin.dsl.module

val databaseTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java).build()
    }
}
