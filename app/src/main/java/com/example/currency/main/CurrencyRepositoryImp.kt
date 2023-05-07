package com.example.currency.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currency.api.Api
import com.example.currency.constants.Constants
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CurrencyRepositoryImp(private val api: Api,private val databaseClass: DatabaseClass):CurrencyRepository  {
    var currencyMutableLiveData: MutableLiveData<CurrencyModel> = MutableLiveData<CurrencyModel>()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var currencyerrorLiveData :MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()

    override fun get_currency():Single<CurrencyModel>  {
        val observable: Single<CurrencyModel> = api.get_currency(Constants.key)
        return observable
        /*compositeDisposable.add(observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> get_error_data(e,databaseClass) }))
        return  currencyMutableLiveData*/
    }

    override fun convert_currency(
        currencyFromKey: String,
        currencyToKey: String,
        amount: Double): Single<ConvertModel> {
        val observable: Single<ConvertModel> = api.convert(Constants.key,currencyFromKey,currencyToKey,amount)
        return observable;
    }

    fun get_error_data(e: Throwable) {
        Log.d("errormmm", "$e")
        currencyerrorLiveData.value = databaseClass.dao?.get_all_currency_symbols()

    }

}