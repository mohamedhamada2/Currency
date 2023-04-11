package com.example.currency.api


import android.icu.util.CurrencyAmount
import com.example.currency.data.CurrencyBase
import com.example.currency.data.CurrencyModel
import com.example.currency.data.models.ConvertModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("symbols")
    fun get_currency(@Header("apikey")apikey:String): Single<CurrencyModel>

    @GET("latest")
    fun get_currency_base(@Header("apikey")apikey:String,@Query("base")base:String): Single<CurrencyBase>

    @GET("convert")
    fun convert(@Header("apikey")apikey:String,@Query("from")from:String,@Query("to")to:String,@Query("amount")amount: Double):Single<ConvertModel>
}