package com.example.currency.di

import com.example.data.local.room.DatabaseClass
import com.example.data.local.sqlite.DBHelper
import com.example.data.remote.Api
import com.example.data.repo.currency.CurrencyRepositoryImp
import com.example.domain.repo.currency.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyRepoModule {

    @Singleton
    @Provides
    fun providesCurrencyRepository(currency_api: Api, databaseClass: DatabaseClass, dbHelper: DBHelper):CurrencyRepository {
        return CurrencyRepositoryImp(currency_api, databaseClass, dbHelper)
    }
}