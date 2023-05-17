package com.example.currency.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.data.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.Language
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailsViewModel  @Inject constructor(var dbHelper: DBHelper,@Named("language")var language: String) : ViewModel() {

       var currencyMutableLiveData: MutableLiveData<List<CurrencyExchange>> = MutableLiveData<List<CurrencyExchange>>()
       var languageMutableLiveData :MutableLiveData<String> = MutableLiveData()
       init {
           get_language(language)
           currencyMutableLiveData.value= dbHelper.readCurrency()
       }

    private fun get_language(language: String) {
        languageMutableLiveData.value = language
    }


}