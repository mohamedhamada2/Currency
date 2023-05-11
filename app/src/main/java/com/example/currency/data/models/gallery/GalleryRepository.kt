package com.example.currency.data.models.gallery

import io.reactivex.rxjava3.core.Observable

interface GalleryRepository {
    fun get_gallery(key:String,query:String,page: Int): Observable<GalleryModel>
}