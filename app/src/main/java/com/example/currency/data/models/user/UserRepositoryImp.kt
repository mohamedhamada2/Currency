package com.example.currency.data.models.user

import android.content.Context
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImp @Inject constructor(var userSharedPreferance: UserSharedPreferance,var context: Context) : UserRepository{
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