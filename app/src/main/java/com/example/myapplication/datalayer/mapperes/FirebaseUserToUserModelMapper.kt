package com.example.myapplication.datalayer.mapperes

import com.example.myapplication.datalayer.Mapper
import com.example.myapplication.datalayer.entities.UserDto
import com.example.myapplication.domainlayer.model.UserModel
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Mapper class for converting between FirebaseUser and UserModel objects.
 */
class FirebaseUserToUserModelMapper @Inject constructor() : Mapper<FirebaseUser, UserModel>() {
    /**
     * Maps a FirebaseUser object to a UserModel object.
     * @param value The FirebaseUser object to be mapped.
     * @return The mapped UserModel object.
     */
    override fun map(value: FirebaseUser): UserModel {
        val userModel = UserModel()
        if (value != null) {
//            userModel.id = value.id
            userModel.displayName = "${value.displayName}"
//            userModel.firstName = value.firstName
//            userModel.lastName = value.lastName
            userModel.email = value.email
            userModel.phoneNumber = value.phoneNumber
//            userModel.token = value.token
        }
        return userModel
    }

    override fun reverseMap(value: UserModel): FirebaseUser {
        TODO("Not yet implemented")
    }
}