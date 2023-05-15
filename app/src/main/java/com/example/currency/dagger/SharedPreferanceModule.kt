package com.example.currency.dagger

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.currency.data.api.Api
import com.example.currency.data.models.currency.CurrencyRepositoryImp
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserRepositoryImp
import com.example.currency.data.models.user.UserSharedPreferance
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    fun providesuserRepository(userSharedPreferance: UserSharedPreferance,@ApplicationContext context: Context) = UserRepositoryImp(userSharedPreferance,context)
}