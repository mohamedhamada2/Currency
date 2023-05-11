package com.example.currency.viewmodel.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.currency.CurrencyRepositoryImp
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.sqlite.DBHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(var currencyRepositoryImp: CurrencyRepositoryImp,
                                        var databaseClass:DatabaseClass,
                                        var dbHelper: DBHelper,
                                        @Named("network_connection")var connect_network:Boolean) : ViewModel() {

    var currencyMutableLiveData: MutableLiveData<CurrencyModel?> = MutableLiveData<CurrencyModel?>()
    var convertcurrencyLiveData: MutableLiveData<ConvertModel> = MutableLiveData<ConvertModel>()
    var currencyLiveData :MutableLiveData<ArrayList<Currency>> = MutableLiveData<ArrayList<Currency>>()
    var currencyvalueMutableLiveData: MutableLiveData< ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    lateinit var currency_single :Single<CurrencyModel>
    lateinit var convert_currency_single: Single<ConvertModel>
    var compositeDisposable:CompositeDisposable = CompositeDisposable()
    var currencylist: ArrayList<Currency> = ArrayList()
    var  currencyvaluelist : ArrayList<String> = ArrayList()
    init {
        get_currency()
    }

    fun get_currency() {
        if (connect_network){
            currency_single = currencyRepositoryImp.get_currency().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            compositeDisposable.add(currency_single.subscribe(
                { o: CurrencyModel? -> setCurrencyData(o) },
                { e: Throwable -> get_error_data(e) }))
        }else{
            get_currency_from_local_db()
        }
    }

    private fun get_currency_from_local_db() {
        currencylist = databaseClass.dao?.get_all_currency_symbols() as ArrayList<Currency>
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
            //currencybasevaluelist.add(currency.key)
        }
        currencyLiveData.value = currencylist
        currencyvalueMutableLiveData.value = currencyvaluelist
    }

    private fun get_error_data(e: Throwable) {
        Log.d("errormmm", "$e")

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun setCurrencyData(
        currencymodel: CurrencyModel?) {
        for (keys in currencymodel?.symbols!!.keys) {
            val value = currencymodel.symbols.getValue(keys)
            val currency = Currency(keys, value)
            //databaseClass?.dao?.AddCurrencySymbols(currency)
            currencylist.add(currency)
            databaseClass.dao?.AddCurrencySymbols(currency)
            currencyMutableLiveData.value = currencymodel
            for (currency in currencylist) {
                currencyvaluelist.add(currency.key)
            }
            currencyvalueMutableLiveData.value = currencyvaluelist
            currencyLiveData.value = currencylist
            //homeFragment.setSpinnerAdapter(currencylist,currencyvaluelist)
        }
    }
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double) {
        if (connect_network) {
            convert_currency_single =
                currencyRepositoryImp.convert_currency(currencyFromKey, currencyToKey, amount)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            compositeDisposable.add(
                convert_currency_single.subscribe(
                    { o: ConvertModel? -> convertcurrencyLiveData.value = o },
                    { e: Throwable -> Log.d("convert_error", "$e") })
            )
        }
    }

    fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }
    /*fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }*/
}