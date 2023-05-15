package com.example.currency.data.models.user

import android.provider.ContactsContract.DisplayPhoto

interface UserRepository {
    fun update_user_data(name:String,photo: String,email:String,password:String):Boolean
    fun get_user_data():UserModel
}