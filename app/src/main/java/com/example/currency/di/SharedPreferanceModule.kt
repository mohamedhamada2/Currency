package com.example.currency.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.data.repo.user.UserRepositoryImp
import com.example.data.local.UserSharedPreferance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("StaticFieldLeak")
@Module
@InstallIn(SingletonComponent::class)
object SharedPreferanceModule {
    var mPrefs: SharedPreferences? = null
    var context: Context? = null
    private lateinit var instance: UserSharedPreferance

    @Singleton
    @Provides
    fun getInstance(): UserSharedPreferance {

        instance = UserSharedPreferance()
        return instance
    }

    @Singleton
    @Provides
    fun providesuserRepository(userSharedPreferance: UserSharedPreferance, @ApplicationContext context: Context) = UserRepositoryImp(userSharedPreferance,context)


}