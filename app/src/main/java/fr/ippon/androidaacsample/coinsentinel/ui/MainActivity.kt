package fr.ippon.androidaacsample.coinsentinel.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import awaitObjectResult
import com.github.kittinunf.fuel.Fuel
import dagger.android.AndroidInjection
import fr.ippon.androidaacsample.coinsentinel.R
import fr.ippon.androidaacsample.coinsentinel.api.CoinResultDeserializer
import fr.ippon.androidaacsample.coinsentinel.api.CoinRouting
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.util.Resource
import fr.ippon.androidaacsample.coinsentinel.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var coinResultDeserializer: CoinResultDeserializer

    private val coins: MutableList<Coin> = mutableListOf()
    private lateinit var coinAdapter: CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        init()
    }

    override fun onResume() {
        super.onResume()

        this.fetchCoins()
        this.swipe_refresh.setOnRefreshListener {
            this.fetchCoins();
        }
    }

    override fun onPause() {
        this.swipe_refresh.setOnRefreshListener(null)
        super.onPause()
    }

    private fun fetchCoins() = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
        getCoins().consumeEach { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    updateCoins(it.data ?: emptyArray())
                    this@MainActivity.swipe_refresh.isRefreshing = false
                }
                Status.ERROR -> {
                    Toast.makeText(this@MainActivity, it.throwable?.message, Toast.LENGTH_SHORT).show()
                    this@MainActivity.swipe_refresh.isRefreshing = false
                }
                Status.LOADING -> this@MainActivity.swipe_refresh.isRefreshing = true
            }
        }
    }

    private fun updateCoins(newCoins: Array<Coin>) {
        coins.clear()
        coins.addAll(newCoins)
        coinAdapter.notifyDataSetChanged()
    }

    suspend private fun getCoins() = GlobalScope.produce(Dispatchers.Default, 0) {
        val emptyData: Array<Coin> = emptyArray()
        send(Resource.loading(emptyData))

        Fuel.request(CoinRouting.GetCoins())
            .awaitObjectResult(coinResultDeserializer)
            .fold(success = { response ->
                send(Resource.success(response.data))
            }, failure = { error ->
                send(Resource.error(error, emptyData))
            })
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this)
        this.recycler_view_coin.layoutManager = layoutManager
        coinAdapter = CoinAdapter(coins, this)
        this.recycler_view_coin.adapter = coinAdapter
        this.swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        coinAdapter = CoinAdapter(coins, this)
        this.recycler_view_coin.adapter = coinAdapter
    }
}
