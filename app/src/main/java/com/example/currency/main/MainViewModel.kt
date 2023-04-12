package com.example.currency.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.api.Api
import com.example.currency.constants.Constants
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.room.DatabaseClass
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
     var currencyMutableLiveData: MutableLiveData<CurrencyModel> = MutableLiveData<CurrencyModel>()
     var convertcurrencyLiveData: MutableLiveData<ConvertModel> = MutableLiveData<ConvertModel>()
     var currencyerrorLiveData :MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()
    fun get_currency(api: Api,databaseClass: DatabaseClass) {
        val observable: Single<CurrencyModel> = api.get_currency(Constants.key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> get_error_data(databaseClass,e) })
    }

    fun get_error_data(databaseClass: DatabaseClass, e: Throwable) {
        Log.d("errormmm", "$e")
        currencyerrorLiveData.value = databaseClass.dao?.get_all_currency_symbols()

    }
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double,api: Api) {

        val observable: Single<ConvertModel> = api.convert(Constants.key,currencyFromKey,currencyToKey,amount).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(
            { o: ConvertModel? -> convertcurrencyLiveData.value= o },
            { e: Throwable -> Log.d("convert_error", "$e") })
    }

}