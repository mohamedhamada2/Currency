package com.example.currency.data.models.user

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class UserSharedPreferance {

    var mPrefs: SharedPreferences? = null

    constructor()


    fun Create_Update_UserData(context: Context, userModel: UserModel?) {
        mPrefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val gson = Gson()
        val userData: String = gson.toJson(userModel)
        val editor = mPrefs!!.edit()
        editor.putString("user_data", userData)
        editor.apply()
        Create_Update_Session(context, "login")
    }

    fun Create_Update_Session(context: Context, session: String?) {
        mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor = mPrefs!!.edit()
        editor.putString("state", session)
        editor.apply()
    }


    fun getSession(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences("session", Context.MODE_PRIVATE)
        return preferences.getString("state", "logout")
    }


    fun Get_UserData(context: Context): UserModel? {
        mPrefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val gson = Gson()
        val userData = mPrefs!!.getString("user_data", "")
        return gson.fromJson(userData, UserModel::class.java)
    }

    fun ClearData(context: Context) {
        val userModel: UserModel? = null
        mPrefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val gson = Gson()
        val userData: String = gson.toJson(userModel)
        val editor = mPrefs!!.edit()
        editor.putString("user_data", userData)
        editor.apply()
        Create_Update_Session(context, "login")
    }
}