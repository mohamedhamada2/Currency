package com.example.currency.data.models.currency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currency.data.api.Api
import com.example.currency.constants.Constants
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.models.gallery.GalleryModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class CurrencyRepositoryImp(var currency_api: Api):
    CurrencyRepository {

    lateinit var currencyobservable :Single<CurrencyModel>
    lateinit var convertcurrencyobservable :Single<ConvertModel>


    override fun get_currency():Single<CurrencyModel>  {
        currencyobservable = currency_api.get_currency(Constants.key)
        return currencyobservable
        /*compositeDisposable.add(observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> get_error_data(e,databaseClass) }))
        return  currencyMutableLiveData*/
    }

    override fun convert_currency(
        currencyFromKey: String,
        currencyToKey: String,
        amount: Double): Single<ConvertModel> {
        convertcurrencyobservable = currency_api.convert(Constants.key,currencyFromKey,currencyToKey,amount)
        return convertcurrencyobservable;
    }

}