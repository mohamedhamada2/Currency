package com.example.currency.dagger

import android.app.Application
import com.alatheer.dagger.ApiModule
import com.alatheer.dagger.AppModule

class MyApplication :Application() {
    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .apiModule(ApiModule("https://api.apilayer.com/fixer/"))
            .appModule( AppModule(this))
            .build()
    }
    fun getNetComponent(): AppComponent {
        return component
    }
}