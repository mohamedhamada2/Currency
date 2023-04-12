package com.example.currency.dagger

import android.content.Context
import androidx.room.Room
import com.example.currency.data.room.DatabaseClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    var context: Context

    constructor(context: Context) {
        this.context = context
    }


    @Provides
    @Singleton
    fun provideRoomDataBase():DatabaseClass{
        var databaseClass = Room.databaseBuilder(context,
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()
        return databaseClass
    }
}