package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.datalayer.entities.LoginResponse
import com.example.myapplication.domainlayer.repository.UserDetailRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Use case for retrieving user details.
 * @property userDetailRepository The repository for user detail-related operations.
 */
class UserDetailUseCase @Inject constructor(private val userDetailRepository: UserDetailRepository) {

    /**
     * Retrieve user details.
     * @return A Response object containing the user detail response data.
     */
    suspend fun getUserDetail(): Response<LoginResponse> {
        return userDetailRepository.getUserDetail()
    }

}