package com.example.currency.di

import com.example.currency.view.main.MainActivity
import com.example.currency.viewmodel.home.HomeViewModel

//@Singleton
//@Component(modules = [AppModule::class,ApiModule::class,RoomModule::class,SqliteModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeViewModel: HomeViewModel)

}