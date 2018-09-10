package fr.ippon.androidaacsample.coinsentinel.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import java.util.*

data class CoinResult (
    @Expose
    @SerializedName("data")
    val data: Array<Coin>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoinResult

        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(data)
    }
}