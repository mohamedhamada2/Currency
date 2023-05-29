package com.example.currency.viewmodel.auth

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RegisterViewModel @Inject constructor(var userRepositoryImp: UserRepositoryImp): ViewModel() {
    var success_save_MutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var languageMutableLiveData :MutableLiveData<String> = MutableLiveData()

    fun save_data(name: String, phone: String, email: String, password: String) {
        val success_save = userRepositoryImp.update_user_data(name,phone,email,password)
        if (success_save){
            success_save_MutableLiveData.value = success_save
        }
    }
}
