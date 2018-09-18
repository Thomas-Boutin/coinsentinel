package fr.ippon.androidaacsample.coinsentinel.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinTypeAdapter
import fr.ippon.androidaacsample.coinsentinel.db.AppDatabase
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.db.CoinDao
import java.lang.reflect.Modifier
import javax.inject.Singleton


@Module
class AppModule {
    private val DATABASE_NAME = "COIN_DB"

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

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase): CoinDao {
        return db.coinDao()
    }
}