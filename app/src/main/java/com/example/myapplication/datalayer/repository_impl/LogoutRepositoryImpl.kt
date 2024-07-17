package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.entities.LogoutDto
import com.example.myapplication.domainlayer.repository.LogoutRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [LogoutRepository] interface for handling logout-related data operations.
 * Utilizes [ApiInterface] for making logout API calls.
 */
class LogoutRepositoryImpl  @Inject constructor(private val apiInterface: ApiInterface) : LogoutRepository {

    /**
     * Perform a logout operation.
     * @return A Response object containing the logout response data.
     */
    override suspend fun logout(): Response<LogoutDto> {
        return  apiInterface.logout()
    }

}