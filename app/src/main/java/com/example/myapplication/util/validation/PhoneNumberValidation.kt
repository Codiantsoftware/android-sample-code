package com.example.myapplication.util.validation

import com.example.myapplication.DemoApplication
import com.example.myapplication.R
import com.example.myapplication.util.isStringNullOrBlank
import javax.inject.Inject

/**
 * Class responsible for validating phone numbers.
 * Provides methods to validate a phone number string and return ValidationResults.
 */
class PhoneNumberValidation @Inject constructor(){

    companion object {
        //Maximum number of characters allowed for a phone number.
        const val MAX_CHAR = 10
    }

    /**
     * Validates the given phone number string.
     * @param number The phone number string to be validated.
     * @return ValidationResult indicating whether the phone number is valid or contains errors.
     */
    fun validatePhoneNumber(number: String?): ValidationResult {
        return when {
            number.isStringNullOrBlank() -> ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_mobile_number_empty) )
            isPhoneNumberLengthValid(number!!) ->  ValidationResult.Error(message = DemoApplication.instance?.getString(R.string.error_mobile_number_length) )
            else -> ValidationResult.Success
        }
    }

    /**
     * Checks if the length of the phone number is valid.
     * @param number The phone number string to be checked.
     * @return true if the phone number length is not equal to the maximum allowed characters, false otherwise.
     */
    private fun isPhoneNumberLengthValid(number: String): Boolean {
        return number.length != MAX_CHAR
    }
}