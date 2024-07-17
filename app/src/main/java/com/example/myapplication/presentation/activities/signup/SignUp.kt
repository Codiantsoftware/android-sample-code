package com.example.myapplication.presentation.activities.signup

/**
 * Data class used to signup process
 *
 * @property firstName
 * @property lastName
 * @property email
 * @property phoneNumber
 * @property password
 */
data class SignUp (
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var password: String? = null
)