package com.example.currency.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currency.R
import com.example.currency.dagger.MyApplication
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper
import com.example.currency.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    lateinit var activityDetailsBinding: ActivityDetailsBinding
    lateinit var detailsViewModel1: DetailsViewModel
    lateinit var exchangelist : List<CurrencyExchange?>
    lateinit var detailsAdapter: DetailsAdapter
    lateinit var layout_manager :LinearLayoutManager
    @Inject
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        detailsViewModel1 = ViewModelProvider(this)[DetailsViewModel::class.java]
        //(application as MyApplication).getAppComponent()!!.inject(this)
        activityDetailsBinding.detailsviewmodel = detailsViewModel1
        detailsViewModel1.get_date(dbHelper)
        detailsViewModel1.currencyMutableLiveData.observe(this, Observer {
            setlist(it)
        })

    }

    private fun setlist(currencyExchanges: List<CurrencyExchange>) {
        detailsAdapter = DetailsAdapter(currencyExchanges)
        layout_manager = LinearLayoutManager(this)
        activityDetailsBinding.detailsRecycler.adapter = detailsAdapter
        activityDetailsBinding.detailsRecycler.layoutManager = layout_manager;
    }
}