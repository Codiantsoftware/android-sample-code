package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.entities.LoginResponse
import com.example.myapplication.domainlayer.repository.LoginRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [LoginRepository] interface for handling login-related data operations.
 * Utilizes [ApiInterface] for making login API calls.
 */
class LoginRepositoryImpl  @Inject constructor(private val apiInterface: ApiInterface) : LoginRepository {

    /**
     * Perform a login operation.
     * @param map A HashMap containing login parameters.
     * @return A Response object containing the login response data.
     */
    override suspend fun login(map: HashMap<String, Any?>): Response<LoginResponse> {
        return  apiInterface.login(map)
    }

}