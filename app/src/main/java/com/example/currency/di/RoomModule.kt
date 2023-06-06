package com.example.currency.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.room.DatabaseClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): DatabaseClass {
        var databaseClass = Room.databaseBuilder(context,
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().addMigrations(DatabaseClass.MIGRATION_3_4).build()
        return databaseClass
    }
}