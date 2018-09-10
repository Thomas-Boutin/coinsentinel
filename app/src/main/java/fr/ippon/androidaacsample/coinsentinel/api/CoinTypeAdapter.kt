package fr.ippon.androidaacsample.coinsentinel.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import java.lang.reflect.Type

class CoinTypeAdapter : JsonDeserializer<Coin> {
    companion object {
        val ID = "id"
        val NAME = "name"
        val SYMBOL = "symbol"
        val RANK = "rank"
        val QUOTES = "quotes"
        val USD = "USD"
        val PRICE = "price"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Coin {
        val jsonCoin: JsonObject  = json?.asJsonObject ?: JsonObject()

        val id: Long = jsonCoin
                .get(ID)
                .asLong
        val name: String = jsonCoin
                .get(NAME)
                .asString
        val code: String = jsonCoin
                .get(SYMBOL)
                .asString
        val rank: Int = jsonCoin
                .get(RANK)
                .asInt
        val price: String = jsonCoin
                .get(QUOTES)
                .asJsonObject
                .get(USD)
                .asJsonObject
                .get(PRICE)
                .asString

        return Coin(id, name, code, rank, price)
    }
}