package com.example.currency.api


import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.models.convert.ConvertModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface Api {
    @GET("symbols")
    fun get_currency(@Header("apikey")apikey:String): Single<CurrencyModel>


    @GET("convert")
    fun convert(@Header("apikey")apikey:String,@Query("from")from:String,@Query("to")to:String,@Query("amount")amount: Double):Single<ConvertModel>
}