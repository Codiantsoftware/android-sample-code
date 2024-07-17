package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.entities.LoginResponse
import com.example.myapplication.domainlayer.repository.UserDetailRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [UserDetailRepository] interface for handling user detail related data operations.
 * Utilizes [ApiInterface] for making API calls to retrieve user details.
 */
class UserDetailRepositoryImpl  @Inject constructor(private val apiInterface: ApiInterface) : UserDetailRepository {

    /**
     * Retrieve user details.
     * @return A Response object containing the user detail response data.
     */
    override suspend fun getUserDetail(): Response<LoginResponse> {
        return  apiInterface.getUserDetail()
    }

}