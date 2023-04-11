package com.example.currency.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.currency.MainViewModel
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
        detailsViewModel1 = ViewModelProvider(this).get(DetailsViewModel::class.java)
        activityDetailsBinding.detailsviewmodel = detailsViewModel1
        /*databaseClass = Room.databaseBuilder(getApplicationContext(),
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()
        exchangelist = databaseClass.dao?.get_all_exchanges()!!*/
        exchangelist = db.readCurrency()!!
        detailsAdapter = DetailsAdapter(exchangelist)
        layout_manager = LinearLayoutManager(this)
        activityDetailsBinding.detailsRecycler.adapter = detailsAdapter
        activityDetailsBinding.detailsRecycler.layoutManager = layout_manager;



    }
}