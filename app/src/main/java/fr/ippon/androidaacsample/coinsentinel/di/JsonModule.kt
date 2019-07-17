package fr.ippon.androidaacsample.coinsentinel.di

import com.google.gson.GsonBuilder
import fr.ippon.androidaacsample.coinsentinel.api.CoinTypeAdapter
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import org.koin.dsl.module
import java.lang.reflect.Modifier

val jsonModule = module {
    single {
        GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .registerTypeAdapter(Coin::class.java, CoinTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
}