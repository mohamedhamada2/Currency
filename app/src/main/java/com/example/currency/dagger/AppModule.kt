package com.alatheer.dagger

import android.app.Application
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides

import javax.inject.Singleton



@Module
class AppModule {
    lateinit var mApplication: Application

    constructor(mApplication: Application) {
        this.mApplication = mApplication
    }


    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }
}