package fr.ippon.androidaacsample.coinsentinel.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "COIN")
data class Coin (
    @PrimaryKey
    val id: Long,
    val name: String,
    val code: String,
    val rank: Int,
    val price: String
)