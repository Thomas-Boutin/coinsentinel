package fr.ippon.androidaacsample.coinsentinel.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import fr.ippon.androidaacsample.coinsentinel.CoinSentinelApp
import javax.inject.Singleton


@Singleton
@Component(modules = [
    (AndroidInjectionModule::class),
    (AppModule::class),
    (MainActivityModule::class)
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(coinSentinelApp: CoinSentinelApp)
}