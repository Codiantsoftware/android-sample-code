package com.example.myapplication.datalayer.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a LoginResponse entity.
 * @property success A Boolean indicating the success status of the login operation.
 * @property userDto The UserDto object containing user data if login is successful.
 * @property message A String containing the message associated with the login operation.
 */

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val userDto: UserDto,
    @SerializedName("message")
    val message: String
)
