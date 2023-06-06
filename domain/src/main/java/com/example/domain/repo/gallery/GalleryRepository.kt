package com.example.domain.repo.gallery

import io.reactivex.rxjava3.core.Observable

interface GalleryRepository {
    fun get_gallery(key:String,query:String,page: Int): Observable<com.example.domain.entity.gallery.GalleryModel>
}