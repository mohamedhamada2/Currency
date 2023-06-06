package com.example.currency.di

import com.example.data.repo.gallery.GalleryRepositoryImp
import com.example.domain.repo.gallery.GalleryRepository
import com.example.domain.usecase.gallery.GetGallery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GalleryUseCaseModule {
    @Singleton
    @Provides
    fun provides_get_gallery(galleryRepository: GalleryRepository):GetGallery{
        return GetGallery(galleryRepository)
    }
}