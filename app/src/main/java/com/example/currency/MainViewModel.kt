package com.example.currency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.api.Api
import com.example.currency.api.RetrofitClientInstance
import com.example.currency.data.CurrencyBase
import com.example.currency.data.CurrencyModel
import com.example.currency.data.models.ConvertModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
     var currencyMutableLiveData: MutableLiveData<CurrencyModel> = MutableLiveData<CurrencyModel>()
     var currencybaseMutableLiveData: MutableLiveData<CurrencyBase> = MutableLiveData<CurrencyBase>()
     var convertcurrencyLiveData: MutableLiveData<ConvertModel> = MutableLiveData<ConvertModel>()
    fun get_currency() {
        val api: Api = RetrofitClientInstance.getInstance().create(Api::class.java)
        val observable: Single<CurrencyModel> = api.get_currency("AicNkK57JTQdJvKAFqEApfPunTBrBjYY").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> Log.d("error", "$e") })
    }

    fun get_currency_base(currencyValue: String) {
        val api: Api = RetrofitClientInstance.getInstance().create(Api::class.java)
        val observable: Single<CurrencyBase> = api.get_currency_base("AicNkK57JTQdJvKAFqEApfPunTBrBjYY",currencyValue).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(
            { o: CurrencyBase? -> currencybaseMutableLiveData.value= o },
            { e: Throwable -> Log.d("errormmm", "$e") })
    }

    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double) {
        val api: Api = RetrofitClientInstance.getInstance().create(Api::class.java)
        val observable: Single<ConvertModel> = api.convert("AicNkK57JTQdJvKAFqEApfPunTBrBjYY",currencyFromKey,currencyToKey,amount).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(
            { o: ConvertModel? -> convertcurrencyLiveData.value= o },
            { e: Throwable -> Log.d("convert_error", "$e") })
    }

}