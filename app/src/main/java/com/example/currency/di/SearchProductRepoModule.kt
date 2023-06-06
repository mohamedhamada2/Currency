package com.example.currency.di

import com.example.data.repo.search.SearchProductsRepositoryImp
import com.example.domain.repo.search.SearchProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object SearchProductRepoModule {
    @Singleton
    @Provides
    fun providesSearchProductRepository(): SearchProductsRepository {
        return SearchProductsRepositoryImp()
    }
}