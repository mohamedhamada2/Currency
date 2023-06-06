package com.example.data.remote


import com.example.domain.entity.convert.ConvertModel
import com.example.domain.entity.currency.CurrencyModel
import com.example.domain.entity.gallery.GalleryModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface Api {
    @GET("symbols")
    fun get_currency(@Header("apikey")apikey:String): Single<CurrencyModel>


    @GET("convert")
    fun convert(@Header("apikey")apikey:String,@Query("from")from:String,@Query("to")to:String,@Query("amount")amount: Double):Single<ConvertModel>

}