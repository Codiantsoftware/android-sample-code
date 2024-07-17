package com.example.myapplication.datalayer.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a UserDto entity.
 */

data class UserDto(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("token")
    var token: String? = null,
    var password: String? = null
)