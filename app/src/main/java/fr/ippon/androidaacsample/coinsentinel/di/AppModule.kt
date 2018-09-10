package fr.ippon.androidaacsample.coinsentinel.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinTypeAdapter
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import java.lang.reflect.Modifier
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .registerTypeAdapter(Coin::class.java, CoinTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    @Singleton @Provides
    fun provideCoinResultDeserializer(gson: Gson): CoinResultDeserializer {
        return CoinResultDeserializer(gson)
    }
}