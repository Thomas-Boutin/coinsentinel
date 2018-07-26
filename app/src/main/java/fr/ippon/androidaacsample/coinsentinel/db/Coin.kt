package fr.ippon.androidaacsample.coinsentinel.db

data class Coin (
    val id: Long,
    val name: String,
    val code: String,
    val rank: Int,
    val price: String
)