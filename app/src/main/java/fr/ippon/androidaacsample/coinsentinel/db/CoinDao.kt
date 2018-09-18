package fr.ippon.androidaacsample.coinsentinel.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coins: Array<Coin>)

    @Query("SELECT * FROM COIN ORDER BY rank ASC")
    fun getAll(): LiveData<Array<Coin>>
}