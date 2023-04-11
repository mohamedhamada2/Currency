package com.example.currency.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.CurrencyModel
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper
import java.io.Closeable

class DetailsViewModel : ViewModel {
    fun get_date() {
        val db = DBHelper(context, null)
        currencyMutableLiveData.value= db.readCurrency()
    }

    var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()
    var context:Context
    var detailsActivity:DetailsActivity

    constructor(context: Context) : super() {
        this.context = context
        detailsActivity = context as DetailsActivity;
    }

}