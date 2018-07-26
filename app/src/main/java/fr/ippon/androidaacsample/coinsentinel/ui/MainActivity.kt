package fr.ippon.androidaacsample.coinsentinel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ippon.androidaacsample.coinsentinel.R
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val coins: List<Coin> = listOf(
        Coin(1, "Bitcoin", "BTC", 1, "300"),
        Coin(2, "Ethereum", "ETH", 2, "200.34422343")
    )
    private lateinit var coinAdapter: CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this)
        this.recycler_view_coin.layoutManager = layoutManager
        coinAdapter = CoinAdapter(coins, this)
        this.recycler_view_coin.adapter = coinAdapter
    }
}
