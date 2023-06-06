package com.example.currency.di

import com.example.data.remote.GalleryApi
import com.example.data.repo.gallery.GalleryRepositoryImp
import com.example.domain.repo.gallery.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class GalleryRepoModule {
    @Singleton
    @Provides
    fun providesgalleryRepository(gallery_api: GalleryApi):GalleryRepository {
        return GalleryRepositoryImp(gallery_api)
    }
}