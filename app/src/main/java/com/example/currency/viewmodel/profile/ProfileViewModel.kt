package com.example.currency.viewmodel.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserRepositoryImp
import com.example.currency.data.models.user.UserSharedPreferance
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(var userRepositoryImp: UserRepositoryImp,@Named("language")var language:String): ViewModel() {
    var userdata_MutableLiveData: MutableLiveData<UserModel> = MutableLiveData<UserModel>()
    var languageMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
    init {
        get_user_data()
        get_language()
    }

    private fun get_language() {
        languageMutableLiveData.value = language
    }

    fun get_user_data(){
        val  userModel = userRepositoryImp.get_user_data()
        userdata_MutableLiveData.value = userModel
    }
}