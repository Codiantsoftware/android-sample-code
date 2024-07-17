package com.example.myapplication.datalayer.repository_impl

import com.example.myapplication.datalayer.api.ServerError
import com.example.myapplication.datalayer.entities.FirebaseLoginResponse
import com.example.myapplication.domainlayer.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): FirebaseLoginResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            return FirebaseLoginResponse(authResult.user!!, null)
        } catch (e: Exception) {
            val serverError = ServerError(null, 0, e.message ?: "Something went wrong");
            return FirebaseLoginResponse(null, serverError)
        }
    }

    override fun logout() {
        auth.signOut()
    }

}