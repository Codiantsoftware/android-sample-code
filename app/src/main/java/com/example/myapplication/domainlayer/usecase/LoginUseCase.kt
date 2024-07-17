package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.datalayer.entities.LoginResponse
import com.example.myapplication.domainlayer.repository.LoginRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Use case for handling login-related operations.
 * @property loginRepository The repository for login-related operations.
 */
class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    /**
     * Perform a login operation.
     * @param map A HashMap containing login parameters.
     * @return A Response object containing the login response data.
     */
    suspend fun login (map: HashMap<String, Any?>): Response<LoginResponse> {
        return loginRepository.login(map)
    }

}