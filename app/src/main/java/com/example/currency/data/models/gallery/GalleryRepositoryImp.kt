package com.example.currency.data.models.gallery

import com.example.currency.data.api.Api
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class GalleryRepositoryImp @Inject constructor(@Named("gallery_api")var gallery_api: Api) :GalleryRepository {
    lateinit var galleryobservable :Observable<GalleryModel>

    override fun get_gallery(key: String, query: String, page: Int): Observable<GalleryModel> {
        galleryobservable = gallery_api.get_gallery(key,query,page)
        return galleryobservable;
    }

}