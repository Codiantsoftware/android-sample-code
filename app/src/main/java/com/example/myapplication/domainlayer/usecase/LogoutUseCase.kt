package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.datalayer.entities.LogoutDto
import com.example.myapplication.domainlayer.repository.LogoutRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Use case for handling logout-related operations.
 * @property logoutRepository The repository for logout-related operations.
 */
class LogoutUseCase @Inject constructor(private val logoutRepository: LogoutRepository) {

    /**
     * Perform a logout operation.
     * @return A Response object containing the logout response data.
     */
    suspend fun logout(): Response<LogoutDto> {
        return logoutRepository.logout()
    }
}