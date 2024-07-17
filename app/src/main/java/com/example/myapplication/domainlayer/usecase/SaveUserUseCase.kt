package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.domainlayer.repository.UserRepository
import javax.inject.Inject

/**
 * Use case for saving user-related information and handling logout.
 * @property userRepository The repository for user-related operations.
 */
class SaveUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Save user-related information.
     * @param userModel The user model to be saved.
     */
    fun saveUser(userModel: UserModel) {
        userRepository.saveUser(userModel)
    }

    /**
     * Logout the user.
     */
    fun logout() {
        userRepository.clearUser()
    }
}