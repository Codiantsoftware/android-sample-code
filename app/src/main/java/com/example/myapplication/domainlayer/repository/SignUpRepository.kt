package com.example.myapplication.domainlayer.repository

import com.example.myapplication.datalayer.entities.SignUpDto
import retrofit2.Response

/**
 * Interface defining sign-up related repository operations.
 */
interface SignUpRepository {

    suspend fun signUp(map: HashMap<String, Any?>) : Response<SignUpDto>
}