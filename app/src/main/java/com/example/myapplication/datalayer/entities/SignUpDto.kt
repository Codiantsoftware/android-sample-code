package com.example.myapplication.datalayer.entities

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a SignUpDto entity.
 */
data class SignUpDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
