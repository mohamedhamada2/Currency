package com.example.currency.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currency.R
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.sqlite.DBHelper
import com.example.currency.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var activityDetailsBinding: ActivityDetailsBinding
    lateinit var detailsViewModel1: DetailsViewModel
    lateinit var  databaseClass :DatabaseClass
    lateinit var exchangelist : List<CurrencyExchange?>
    lateinit var detailsAdapter: DetailsAdapter
    lateinit var layout_manager :LinearLayoutManager
    val db = DBHelper(this, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        //detailsViewModel1 = ViewModelProvider(this).get(DetailsViewModel::class.java)
        detailsViewModel1 = DetailsViewModel(this)
        activityDetailsBinding.detailsviewmodel = detailsViewModel1
        /*databaseClass = Room.databaseBuilder(getApplicationContext(),
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()
        exchangelist = databaseClass.dao?.get_all_exchanges()!!*/
        detailsViewModel1.get_date()
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