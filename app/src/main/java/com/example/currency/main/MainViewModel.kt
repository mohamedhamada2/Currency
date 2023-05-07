package com.example.currency.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(var currencyRepositoryImp: CurrencyRepositoryImp,var databaseClass:DatabaseClass,var dbHelper: DBHelper) : ViewModel() {
    var currencyMutableLiveData: MutableLiveData<CurrencyModel> = MutableLiveData<CurrencyModel>()
    var convertcurrencyLiveData: MutableLiveData<ConvertModel> = MutableLiveData<ConvertModel>()
    var currencyerrorLiveData :MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()
    lateinit var currency_single :Single<CurrencyModel>
    lateinit var convert_currency_single: Single<ConvertModel>
    var compositeDisposable:CompositeDisposable = CompositeDisposable()
    var currencylist: ArrayList<Currency> = ArrayList()
    var  currencyvaluelist : ArrayList<String> = ArrayList()
    fun get_currency(mainActivity: MainActivity) {
        //application.getAppComponent().inject(this)
        currency_single = currencyRepositoryImp.get_currency().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        compositeDisposable.add(currency_single.subscribe(
            { o: CurrencyModel? -> setCurrencyData(o,mainActivity) },
            { e: Throwable -> get_error_data(e,databaseClass) }))
       /* val observable: Single<CurrencyModel> = api.get_currency(Constants.key)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        compositeDisposable.add(observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> get_error_data(e,databaseClass) }))*/
    }

    private fun get_error_data(e: Throwable, databaseClass: DatabaseClass) {
        Log.d("errormmm", "$e")
        currencyerrorLiveData.value = databaseClass.dao?.get_all_currency_symbols()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun setCurrencyData(currencymodel: CurrencyModel?,mainActivity: MainActivity) {
        for (keys in currencymodel?.symbols!!.keys) {
            var value = currencymodel?.symbols!!.getValue(keys)
            var currency = Currency(keys, value)
            //databaseClass?.dao?.AddCurrencySymbols(currency)
            currencylist.add(currency)
            databaseClass.dao?.AddCurrencySymbols(currency)
            currencyMutableLiveData.value = currencymodel
            for (currency in currencylist) {
                currencyvaluelist.add(currency.key)
            }
            mainActivity.setSpinnerAdapter(currencylist,currencyvaluelist)
        }
    }
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double) {

            convert_currency_single = currencyRepositoryImp.convert_currency(currencyFromKey, currencyToKey, amount)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            compositeDisposable.add(
                convert_currency_single.subscribe(
                    { o: ConvertModel? -> convertcurrencyLiveData.value = o },
                    { e: Throwable -> Log.d("convert_error", "$e") })
            )
        }

    fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }
    /*fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }*/
}