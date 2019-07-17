package fr.ippon.androidaacsample.coinsentinel

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import fr.ippon.androidaacsample.coinsentinel.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CoinSentinelApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            initializeDebugConfiguration()
        }

        setUpDependencyInjection()
    }

    private fun setUpDependencyInjection() {
        startKoin {
            androidContext(this@CoinSentinelApp)
            modules(
                listOf(
                    daoModule,
                    databaseModule,
                    deserializerModule,
                    jsonModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initializeDebugConfiguration() {
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}