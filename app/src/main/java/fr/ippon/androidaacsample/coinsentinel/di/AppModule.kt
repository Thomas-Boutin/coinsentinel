package fr.ippon.androidaacsample.coinsentinel.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinTypeAdapter
import fr.ippon.androidaacsample.coinsentinel.db.AppDatabase
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.repository.CoinRepository
import fr.ippon.androidaacsample.coinsentinel.vm.CoinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.lang.reflect.Modifier

private const val DATABASE_NAME = "COIN_DB"

val appModule = module {
    single {
        GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .registerTypeAdapter(Coin::class.java, CoinTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
    single { CoinResultDeserializer(get()) }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
    single {
        get<AppDatabase>().coinDao()
    }
    single { CoinRepository(get(), get()) }
    viewModel { CoinViewModel(get()) }
}