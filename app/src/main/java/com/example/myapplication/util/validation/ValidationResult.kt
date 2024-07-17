package com.example.myapplication.util.validation

/**
 * Sealed class representing the result of a validation operation.
 * It can either be a Success indicating that the validation was successful,
 * or an Error containing an error message indicating validation failure.
 */
sealed class ValidationResult {

    // Represents a successful validation.
    data object Success : ValidationResult()

    //Represents a validation error with an optional error message.
    data class Error(val message: String?): ValidationResult()
}
