package com.example.currency.di

import com.example.domain.repo.currency.CurrencyRepository
import com.example.domain.usecase.currency.GetCurrency
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyUseCaseModule {
    @Singleton
    @Provides
    fun provides_get_currency(currencyRepository: CurrencyRepository) = GetCurrency(currencyRepository)
}