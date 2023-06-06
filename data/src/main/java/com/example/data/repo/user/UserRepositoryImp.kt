package com.example.data.repo.user

import android.content.Context
import com.example.data.local.UserSharedPreferance
import com.example.domain.entity.user.UserModel
import com.example.domain.repo.user.UserRepository


class UserRepositoryImp (var userSharedPreferance: UserSharedPreferance, var context: Context) :
    UserRepository {
    var save_date:Boolean = false
    override fun update_user_data(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Boolean {
        val userModel = UserModel(name,phone,email,password)
        userSharedPreferance.Create_Update_UserData(context,userModel)
        save_date = true
        return save_date
    }

    override fun get_user_data(): UserModel {
        val userModel = userSharedPreferance.Get_UserData(context)
        return userModel!!
    }



}