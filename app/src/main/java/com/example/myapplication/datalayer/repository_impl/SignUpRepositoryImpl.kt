package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.entities.SignUpDto
import com.example.myapplication.domainlayer.repository.SignUpRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [SignUpRepository] interface for handling sign-up related data operations.
 * Utilizes [ApiInterface] for making sign-up API calls.
 */
class SignUpRepositoryImpl  @Inject constructor(private val apiInterface: ApiInterface) : SignUpRepository {

    /**
     * Perform a sign-up operation.
     * @param map A HashMap containing sign-up parameters.
     * @return A Response object containing the sign-up response data.
     */
    override suspend fun signUp(map: HashMap<String, Any?>): Response<SignUpDto> {
        return  apiInterface.signUp(map)
    }

}