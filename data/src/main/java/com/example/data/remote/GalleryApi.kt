package com.example.data.remote

import com.example.domain.entity.gallery.GalleryModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GalleryApi {
    @GET("search")
    fun get_gallery(@Header("Authorization")key:String, @Query("query")query:String, @Query("page")page: Int): Observable<GalleryModel>
}