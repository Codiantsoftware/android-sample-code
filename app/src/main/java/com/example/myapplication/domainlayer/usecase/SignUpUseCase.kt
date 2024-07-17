package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.datalayer.entities.SignUpDto
import com.example.myapplication.domainlayer.repository.SignUpRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Use case for handling sign-up related operations.
 * @property signUpRepository The repository for sign-up related operations.
 */
class SignUpUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    /**
     * Perform a sign-up operation.
     * @param map A HashMap containing sign-up parameters.
     * @return A Response object containing the sign-up response data.
     */
    suspend fun signup(map: HashMap<String, Any?>): Response<SignUpDto> {
        return signUpRepository.signUp(map)
    }

}