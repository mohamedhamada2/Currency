package com.example.currency.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class DetailsViewModel : ViewModel() {
    var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()

    fun get_date(dbHelper: DBHelper) {
        currencyMutableLiveData.value= dbHelper.readCurrency()
    }

}