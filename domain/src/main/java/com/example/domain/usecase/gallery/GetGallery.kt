package com.example.domain.usecase.gallery

import com.example.domain.repo.gallery.GalleryRepository

class GetGallery(private val galleryRepository: GalleryRepository) {
    fun get_gallery(key:String,query:String,page: Int) = galleryRepository.get_gallery(key,query,page)
}