package fr.ippon.androidaacsample.coinsentinel

import fr.ippon.androidaacsample.coinsentinel.di.*
import fr.ippon.androidaacsample.coinsentinel.util.databaseTestModule
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class ConfigurationTest: KoinTest {
    private lateinit var app: CoinSentinelApp

    @Before
    fun setUp() {
        app = mock(CoinSentinelApp::class.java)
    }

    @Test
    fun modulesInitializationShouldBeOK() {
        koinApplication {
            androidContext(app)
            modules(
                listOf(
                    daoModule,
                    databaseTestModule,
                    deserializerModule,
                    jsonModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }.checkModules()
    }
}
