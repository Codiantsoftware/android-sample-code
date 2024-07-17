package com.example.myapplication.datalayer.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a LogoutDto entity.
 */
data class LogoutDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
