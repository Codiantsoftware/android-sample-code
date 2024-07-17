package com.example.myapplication.domainlayer.repository

import com.example.myapplication.datalayer.entities.LoginResponse
import retrofit2.Response

/**
 * Interface defining user detail-related repository operations.
 */
interface UserDetailRepository {

    suspend fun getUserDetail() : Response<LoginResponse>
}