package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.domainlayer.repository.UserRepository
import com.example.myapplication.local.AppPreference
import com.example.myapplication.local.PreferenceKeys
import javax.inject.Inject

/**
 * Concrete implementation of [UserRepository] interface for managing user data.
 * Uses [AppPreference] for storing and retrieving user data locally.
 */
class UserRepositoryImpl @Inject constructor() : UserRepository {
    /**
     * Save user data locally.
     * @param userModel The user data to be saved.
     */
    override fun saveUser(userModel: UserModel) {
        AppPreference.put(userModel, PreferenceKeys.USER_DATA)
    }

    /**
     * Retrieve user data from local storage.
     * @return The user data if available, null otherwise.
     */
    override fun getUser(): UserModel? {
        return AppPreference.get<UserModel>(PreferenceKeys.USER_DATA)
    }

    /**
     * Clear user data from local storage.
     */
    override fun clearUser() {
        AppPreference.clearSharedPreference()
    }
}