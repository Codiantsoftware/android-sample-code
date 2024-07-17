package com.example.myapplication.domainlayer.usecase

import com.example.myapplication.datalayer.entities.FirebaseLoginResponse
import com.example.myapplication.domainlayer.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun loginViaGoogle(googleCredential: AuthCredential): FirebaseLoginResponse {
        return authRepository.firebaseSignInWithGoogle(googleCredential)
    }

    fun logout() {
        authRepository.logout()
    }
}