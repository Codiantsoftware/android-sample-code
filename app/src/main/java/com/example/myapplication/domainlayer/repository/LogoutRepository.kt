package com.example.myapplication.domainlayer.repository

import com.example.myapplication.datalayer.entities.LogoutDto
import retrofit2.Response

/**
 * Interface defining logout-related repository operations.
 */
interface LogoutRepository {

    suspend fun logout() : Response<LogoutDto>
}