package com.example.myapplication.domainlayer.repository

import com.example.myapplication.datalayer.entities.LoginResponse
import retrofit2.Response

/**
 * Interface defining login-related repository operations.
 */
interface LoginRepository {

    suspend fun login(map: HashMap<String, Any?>) : Response<LoginResponse>
}