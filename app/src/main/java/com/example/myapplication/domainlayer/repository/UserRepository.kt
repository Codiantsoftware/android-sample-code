package com.example.myapplication.domainlayer.repository

import com.example.myapplication.domainlayer.model.UserModel

/**
 * Interface defining user-related repository operations.
 */
interface UserRepository {

    fun saveUser(userModel: UserModel)
    fun getUser(): UserModel?
    fun clearUser()
}