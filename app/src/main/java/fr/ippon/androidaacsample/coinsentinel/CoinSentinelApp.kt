package fr.ippon.androidaacsample.coinsentinel

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import dagger.android.HasActivityInjector
import timber.log.Timber
import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import fr.ippon.androidaacsample.coinsentinel.di.DaggerAppComponent
import javax.inject.Inject

class CoinSentinelApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            initializeDebugConfiguration()
        }

        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
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

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidActivityInjector
    }
}