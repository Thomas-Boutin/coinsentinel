package fr.ippon.androidaacsample.coinsentinel.api

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting
import fr.ippon.androidaacsample.coinsentinel.BuildConfig

sealed class CoinRouting : FuelRouting {
    override val basePath = BuildConfig.API_BASE_URL

    class GetCoins(
            override val bytes: ByteArray? = ByteArray(0),
            override val body: String? = "") : CoinRouting()

    override val method: Method
        get() {
            when(this) {
                is GetCoins -> return Method.GET
            }
        }

    override val path: String
        get() {
            return when(this) {
                is GetCoins -> "ticker/"
            }
        }

    override val params: List<Pair<String, Any?>>?
        get() {
            return when(this) {
                is GetCoins -> listOf("structure" to "array")
            }
        }

    override val headers: Map<String, String>?
        get() {
            return null
        }
}