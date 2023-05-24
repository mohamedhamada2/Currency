package com.example.currency.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.paperdb.Paper
import org.intellij.lang.annotations.Language
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LanuageModule {
    lateinit var language: String

    @Singleton
    @Provides
    @Named("language")
    fun read_language(@ApplicationContext context: Context): String {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("language","").equals("")){
            language = "en"
        }else{
            language = sharedPreferences.getString("language","")!!
        }
        return language
    }
}