package com.example.myapplication.util.validation

import com.example.myapplication.DemoApplication
import com.example.myapplication.R
import com.example.myapplication.util.isStringNullOrBlank
import javax.inject.Inject

/**
 * Class responsible for validating first and last names.
 * Provides methods to validate a first name and a last name string and return ValidationResults.
 */
class NameValidation @Inject constructor(){

    fun validateFirstName(firstName: String? ): ValidationResult {
        return when {
            firstName.isStringNullOrBlank() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_first_name_empty) )
            else -> ValidationResult.Success
        }
    }

    fun validateLastName(lastName: String? ): ValidationResult {
        return when {
            lastName.isStringNullOrBlank() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_last_name_empty) )
            else -> ValidationResult.Success
        }
    }

}