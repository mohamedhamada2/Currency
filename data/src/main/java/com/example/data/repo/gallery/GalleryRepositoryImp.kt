package com.example.data.repo.gallery

import com.example.data.remote.Api
import com.example.data.remote.GalleryApi
import com.example.domain.entity.gallery.GalleryModel
import com.example.domain.repo.gallery.GalleryRepository
import io.reactivex.rxjava3.core.Observable


class GalleryRepositoryImp (var gallery_api: GalleryApi) :
    GalleryRepository {
    lateinit var galleryobservable :Observable<GalleryModel>
    override fun get_gallery(key: String, query: String, page: Int): Observable<GalleryModel> {
        galleryobservable = gallery_api.get_gallery(key,query,page)
        return galleryobservable
    }

}