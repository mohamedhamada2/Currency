package com.example.currency.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper

class DetailsViewModel : ViewModel {
    fun get_date(dbHelper: DBHelper) {

        currencyMutableLiveData.value= dbHelper.readCurrency()
    }

    var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()
    var context:Context
    var detailsActivity:DetailsActivity

    constructor(context: Context) : super() {
        this.context = context
        detailsActivity = context as DetailsActivity;
    }

}