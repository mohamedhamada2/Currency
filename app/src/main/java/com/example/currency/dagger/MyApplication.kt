package com.example.currency.dagger

import android.app.Application
import com.alatheer.dagger.AppModule
import com.example.currency.constants.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication :Application() {
    /*lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()

    }
    fun getAppComponent(): AppComponent {
        return component
    }*/
}