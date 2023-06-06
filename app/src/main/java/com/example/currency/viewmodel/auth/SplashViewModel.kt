package com.example.currency.viewmodel.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.local.UserSharedPreferance
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(var userSharedPreferance: UserSharedPreferance, @ApplicationContext var context: Context): ViewModel() {
    var UserSharedPreferanceMutableLiveData: MutableLiveData<UserSharedPreferance> = MutableLiveData<UserSharedPreferance>()
    init {
        //val splashActivity: SplashActivity = context as SplashActivity
        UserSharedPreferanceMutableLiveData.value = userSharedPreferance
        //splashActivity.setSharedPreferance(userSharedPreferance)
    }
}