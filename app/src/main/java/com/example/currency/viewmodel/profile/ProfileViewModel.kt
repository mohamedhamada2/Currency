package com.example.currency.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.user.UserModel
import com.example.data.repo.user.UserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var userRepositoryImp: UserRepositoryImp): ViewModel() {
    var userdata_MutableLiveData: MutableLiveData<UserModel> = MutableLiveData<UserModel>()
    var languageMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
    init {
        get_user_data()
        //get_language()
    }

    /*private fun get_language() {
        languageMutableLiveData.value = language
    }*/

    fun get_user_data(){
        val  userModel = userRepositoryImp.get_user_data()
        userdata_MutableLiveData.value = userModel
    }
}