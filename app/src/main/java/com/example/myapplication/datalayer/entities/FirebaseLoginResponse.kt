package com.example.myapplication.datalayer.entities

import com.example.myapplication.datalayer.api.Failure
import com.example.myapplication.datalayer.mapperes.FirebaseUserToUserModelMapper
import com.example.myapplication.domainlayer.model.UserModel
import com.google.firebase.auth.FirebaseUser

data class FirebaseLoginResponse(
    val firebaseUser: FirebaseUser? = null,
    val failure: Failure? = null
)
