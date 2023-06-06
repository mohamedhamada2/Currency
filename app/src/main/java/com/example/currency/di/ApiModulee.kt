package com.example.currency.di

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.currency.R
import com.example.data.constants.Constants
import com.example.data.local.room.DatabaseClass
import com.example.data.repo.currency.CurrencyRepositoryImp
import com.example.data.repo.search.SearchProductsRepositoryImp
import com.example.data.local.sqlite.DBHelper
import com.example.data.remote.Api
import com.example.data.remote.GalleryApi
import com.example.data.repo.gallery.GalleryRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModulee {
    var BASE_URL = Constants.url
    var Gallery_URL = Constants.gallery_url

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    @Named("currency")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
    @Singleton
    @Provides
    @Named("gallery")
    fun provideGalleryRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(Gallery_URL)
        .client(okHttpClient)
        .build()
    @Singleton
    @Provides
    fun provideApiService(@Named("currency")retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun providegalleryApiService(@Named("gallery")retrofit: Retrofit): GalleryApi = retrofit.create(GalleryApi::class.java)
    @Singleton
    @Provides
    @Named("network_connection")
    fun isNetworkAvailable(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected)) {
            Toast.makeText(
                context,
                context.getString(R.string.checkNetworkConnection),
                Toast.LENGTH_LONG
            ).show()
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @Singleton
    @Provides
    fun providesSearchProductRepository() = SearchProductsRepositoryImp()

}