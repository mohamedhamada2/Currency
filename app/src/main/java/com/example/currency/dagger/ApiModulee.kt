package com.example.currency.dagger

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.currency.R
import com.example.currency.data.api.Api
import com.example.currency.constants.Constants
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.models.currency.CurrencyRepositoryImp
import com.example.currency.data.models.gallery.GalleryRepositoryImp
import com.example.currency.data.sqlite.DBHelper
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
            .connectTimeout(30, TimeUnit.SECONDS)
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
    @Named("currency_api")
    fun provideApiService(@Named("currency") retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    @Named("gallery_api")
    fun providegalleryApiService(@Named("gallery") retrofit: Retrofit): Api = retrofit.create(Api::class.java)
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
    fun providesCurrencyRepository(@Named("currency_api")currency_api: Api,databaseClass: DatabaseClass,dbHelper: DBHelper) = CurrencyRepositoryImp(currency_api,databaseClass,dbHelper)

    @Singleton
    @Provides
    fun providesgalleryRepository(@Named("gallery_api")gallery_api: Api) = GalleryRepositoryImp(gallery_api)

}