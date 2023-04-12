package com.example.currency.dagger

import com.alatheer.dagger.ApiModule
import com.alatheer.dagger.AppModule
import com.example.currency.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,ApiModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}