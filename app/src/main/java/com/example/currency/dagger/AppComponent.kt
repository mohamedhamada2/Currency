package com.example.currency.dagger

import com.example.currency.details.DetailsActivity
import com.example.currency.main.MainActivity
import com.example.currency.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

//@Singleton
//@Component(modules = [AppModule::class,ApiModule::class,RoomModule::class,SqliteModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainViewModel: MainViewModel)
    fun inject(detailsActivity: DetailsActivity)
}