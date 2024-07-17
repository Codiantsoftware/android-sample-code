package com.example.myapplication.datalayer.mapperes

import com.example.myapplication.datalayer.Mapper
import com.example.myapplication.datalayer.entities.UserDto
import com.example.myapplication.domainlayer.model.UserModel
import javax.inject.Inject

/**
 * Mapper class for converting between UserDto and UserModel objects.
 */
class UserMapper @Inject constructor() : Mapper<UserDto?, UserModel>() {
    /**
     * Maps a UserDto object to a UserModel object.
     * @param value The UserDto object to be mapped.
     * @return The mapped UserModel object.
     */
    override fun map(value: UserDto?): UserModel {
        val userModel = UserModel()
        if (value != null) {
            userModel.id = value.id
            userModel.displayName = "${value.firstName} ${value.lastName}"
            userModel.firstName = value.firstName
            userModel.lastName = value.lastName
            userModel.email = value.email
            userModel.phoneNumber = value.phoneNumber
            userModel.token = value.token
        }
        return userModel
    }

    override fun reverseMap(value: UserModel): UserDto {
        TODO("Not yet implemented")
    }
}