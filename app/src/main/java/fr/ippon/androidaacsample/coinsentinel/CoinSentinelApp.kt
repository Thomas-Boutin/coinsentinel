package fr.ippon.androidaacsample.coinsentinel

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import timber.log.Timber

class CoinSentinelApp : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            initializeDebugConfiguration()
        }

        super.onCreate()
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