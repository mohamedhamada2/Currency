package com.example.currency.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.local.room.CurrencyExchange
import com.example.data.local.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.Language
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailsViewModel  @Inject constructor(var dbHelper: DBHelper) : ViewModel() {

       var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()
       var languageMutableLiveData :MutableLiveData<String> = MutableLiveData()
       init {
           currencyMutableLiveData.value= dbHelper.readCurrency()
       }

}