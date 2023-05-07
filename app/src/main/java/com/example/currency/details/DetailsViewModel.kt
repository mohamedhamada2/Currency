package com.example.currency.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel  @Inject constructor(var dbHelper: DBHelper) : ViewModel() {
    init {
        var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()
        currencyMutableLiveData.value= dbHelper.readCurrency()
    }

}