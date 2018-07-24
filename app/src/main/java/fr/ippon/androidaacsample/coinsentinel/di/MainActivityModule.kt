package fr.ippon.androidaacsample.coinsentinel.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.ippon.androidaacsample.coinsentinel.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}