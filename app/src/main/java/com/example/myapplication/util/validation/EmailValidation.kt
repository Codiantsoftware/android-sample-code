package com.example.myapplication.util.validation

import com.example.myapplication.DemoApplication
import com.example.myapplication.R
import com.example.myapplication.util.isInvalidEmail
import com.example.myapplication.util.isStringNullOrBlank
import javax.inject.Inject


/**
 * Class responsible for validating email addresses.
 * Provides a method to validate an email string and return a ValidationResult.
 */
class EmailValidation @Inject constructor(){

    /**
     * Validates the given email string.
     * @param email The email string to be validated.
     * @return ValidationResult indicating whether the email is valid or contains errors.
     */
    fun validateEmail(email: String?): ValidationResult {
        return when {
            email.isStringNullOrBlank() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_email) )
            email!!.isInvalidEmail() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_invalid_email) )
            else -> ValidationResult.Success
        }
    }
}