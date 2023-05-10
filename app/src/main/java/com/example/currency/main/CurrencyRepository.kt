package com.example.currency.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.currency.api.Api
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.images.GalleryModel

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyRepository {

    fun get_currency(): Single<CurrencyModel>
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double):Single<ConvertModel>

    fun get_gallery(key:String,query:String,page: Int): Observable<GalleryModel>



}