package com.example.currency.viewmodel.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserRepositoryImp
import com.example.currency.data.models.user.UserSharedPreferance
import com.example.currency.view.register.RegisterActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(var userRepositoryImp: UserRepositoryImp): ViewModel() {
    var success_save_MutableLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    fun save_data(name: String, phone: String, email: String, password: String) {
        val success_save = userRepositoryImp.update_user_data(name,phone,email,password)
        if (success_save){
            success_save_MutableLiveData.value = success_save
        }
    }

}