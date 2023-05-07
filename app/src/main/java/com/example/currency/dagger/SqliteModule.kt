package com.example.currency.dagger

import android.content.Context
import com.example.currency.data.sqlite.DBHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SqliteModule {

    @Provides
    @Singleton
    fun providesSQlite(@ApplicationContext context: Context):DBHelper{
        val db = DBHelper(context, null)
        return db
    }
}