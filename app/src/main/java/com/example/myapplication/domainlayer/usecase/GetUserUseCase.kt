package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.domainlayer.repository.UserRepository
import javax.inject.Inject

/**
 * Use case for retrieving user-related information.
 * @property userRepository The repository for user-related operations.
 */
class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Check if a user is logged in.
     * @return true if a user is logged in, otherwise false.
     */
    @Suppress("unused")
    fun isUserLoggedIn(): Boolean {
        return userRepository.getUser() != null
    }

    /**
     * Get the user model.
     * @return The user model if available, otherwise null.
     */
    fun getUser(): UserModel? {
        return userRepository.getUser()
    }
}