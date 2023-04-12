package com.example.currency.dagger

import android.content.Context
import com.example.currency.data.sqlite.DBHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SqliteModule {
    var context: Context

    constructor(context: Context) {
        this.context = context
    }
    @Provides
    @Singleton
    fun providesSQlite():DBHelper{
        val db = DBHelper(context, null)
        return db
    }
}