package com.example.myapplication.util.validation

import com.example.myapplication.DemoApplication
import com.example.myapplication.R
import com.example.myapplication.util.isStringNullOrBlank
import javax.inject.Inject

/**
 * Class responsible for validating passwords.
 * Provides methods to validate a password string and return ValidationResults.
 */
class PasswordValidation @Inject constructor(){

    companion object {
        const val MIN_CHAR = 2
    }

    /**
     * Validates the given password string.
     * @param password The password string to be validated.
     * @return ValidationResult indicating whether the password is valid or contains errors.
     */
    fun validatePassword(password: String?): ValidationResult {
        return when {
            password.isStringNullOrBlank() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_password_empty) )
            isPasswordLengthValid(password!!) ->  ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_password_min_char) )
            else -> ValidationResult.Success
        }
    }

    /**
     * Checks if the length of the password is valid.
     * @param password The password string to be checked.
     * @return true if the password length is less than the minimum required characters, false otherwise.
     */
    private fun isPasswordLengthValid(password: String): Boolean {
        // Minimum number of characters required for a valid password.
        return password.length < MIN_CHAR
    }
}