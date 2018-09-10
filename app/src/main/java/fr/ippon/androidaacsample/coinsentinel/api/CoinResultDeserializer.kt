package fr.ippon.androidaacsample.coinsentinel.api

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class CoinResultDeserializer (private var gson: Gson) : ResponseDeserializable<CoinResult> {

    override fun deserialize(content: String): CoinResult {
       return gson.fromJson(content, CoinResult::class.java)
    }
}