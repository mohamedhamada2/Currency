package com.example.domain.repo.user

import com.example.domain.entity.user.UserModel

interface UserRepository {
    fun update_user_data(name:String?,photo: String?,email:String?,password:String,user_img:String):Boolean
    fun get_user_data(): UserModel
}