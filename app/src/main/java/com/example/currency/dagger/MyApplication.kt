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
        component = DaggerAppComponent.builder()
            .apiModule(ApiModule(Constants.url))
            .appModule( AppModule(this))
            .roomModule(RoomModule(applicationContext))
            .sqliteModule(SqliteModule(applicationContext))
            .build()
    }
    fun getAppComponent(): AppComponent {
        return component
    }*/
}