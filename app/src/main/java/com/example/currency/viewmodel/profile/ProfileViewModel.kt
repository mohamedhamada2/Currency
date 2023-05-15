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
@HiltViewModel
class ProfileViewModel @Inject constructor(var userRepositoryImp: UserRepositoryImp): ViewModel() {
    var userdata_MutableLiveData: MutableLiveData<UserModel> = MutableLiveData<UserModel>()
    init {
        get_data()
    }
    fun get_data(){
        val  userModel = userRepositoryImp.get_user_data()
        userdata_MutableLiveData.value = userModel
    }
}