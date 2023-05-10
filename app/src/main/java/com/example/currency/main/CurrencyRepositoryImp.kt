package com.example.currency.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currency.api.Api
import com.example.currency.constants.Constants
import com.example.currency.constants.Utilities
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.images.GalleryModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

class CurrencyRepositoryImp(var currency_api: Api,var gallery_api: Api,private val databaseClass: DatabaseClass):CurrencyRepository  {

    var currencyerrorLiveData :MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()
    lateinit var currencyobservable :Single<CurrencyModel>
    lateinit var convertcurrencyobservable :Single<ConvertModel>
    lateinit var galleryobservable :Observable<GalleryModel>

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

    override fun get_gallery(key: String, query: String, page: Int): Observable<GalleryModel> {
     galleryobservable = gallery_api.get_gallery(key,query,page)
     return galleryobservable;
    }

    fun get_error_data(e: Throwable) {
        Log.d("errormmm", "$e")
        currencyerrorLiveData.value = databaseClass.dao?.get_all_currency_symbols()

    }

}