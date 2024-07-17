package com.example.myapplication.domainlayer.repository

import com.example.myapplication.datalayer.entities.FirebaseLoginResponse
import com.google.firebase.auth.AuthCredential

/**
 * Interface defining google sign in-related repository operations.
 */
interface AuthRepository {

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): FirebaseLoginResponse

    fun logout()
}