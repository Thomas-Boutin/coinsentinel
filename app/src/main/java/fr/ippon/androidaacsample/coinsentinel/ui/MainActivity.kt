package fr.ippon.androidaacsample.coinsentinel.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ippon.androidaacsample.coinsentinel.R
import fr.ippon.androidaacsample.coinsentinel.db.Coin
import fr.ippon.androidaacsample.coinsentinel.repository.Resource
import fr.ippon.androidaacsample.coinsentinel.repository.Status
import fr.ippon.androidaacsample.coinsentinel.vm.CoinViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val coinViewModel: CoinViewModel by viewModel()
    private val coinAdapter: CoinAdapter by lazy {
       CoinAdapter(coins, this)
    }
    private val coins: MutableList<Coin> = mutableListOf()

    private val updateCoins = Observer<Resource<Array<Coin>>> {
        refreshCoinsList(it.data ?: emptyArray())

        when (it.status) {
            Status.SUCCESS -> {
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.ERROR -> {
                Toast.makeText(this@MainActivity, it.throwable?.message, Toast.LENGTH_SHORT).show()
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.LOADING -> this@MainActivity.swipe_refresh.isRefreshing = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()

        this.coinViewModel.fetchCoins()
        this.swipe_refresh.setOnRefreshListener {
            this.coinViewModel.fetchCoins()
        }
    }

    override fun onPause() {
        this.swipe_refresh.setOnRefreshListener(null)
        super.onPause()
    }

    private fun refreshCoinsList(newCoins: Array<Coin>) {
        coins.clear()
        coins.addAll(newCoins)
        coinAdapter.notifyDataSetChanged()
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this)
        this.recycler_view_coin.layoutManager = layoutManager
        this.recycler_view_coin.adapter = coinAdapter
        this.swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        this.coinViewModel.coins.observe(this, this.updateCoins)
    }
}
