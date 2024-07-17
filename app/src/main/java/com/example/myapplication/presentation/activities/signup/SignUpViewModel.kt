package com.example.myapplication.presentation.activities.signup

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.domainlayer.usecase.SignUpUseCase
import com.example.myapplication.presentation.base.BaseViewModel
import com.example.myapplication.util.Constants
import com.example.myapplication.util.validation.EmailValidation
import com.example.myapplication.util.validation.NameValidation
import com.example.myapplication.util.validation.PasswordValidation
import com.example.myapplication.util.validation.PhoneNumberValidation
import com.example.myapplication.util.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel class for the sign-up functionality.
 * Manages user input validation and the sign-up process.
 * @param signUpUseCase: Use case responsible for signing up users.
 * @param emailValidation: Validator for email input.
 * @param passwordValidation: Validator for password input.
 * @param nameValidation: Validator for name input.
 * @param phoneNumberValidation: Validator for phone number input.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
    private val nameValidation: NameValidation,
    private val phoneNumberValidation: PhoneNumberValidation
): BaseViewModel<SignUpNavigator>() {

    var signUp = MutableLiveData<SignUp?>()  // LiveData holding the sign-up data.
    var validationError = MutableLiveData<String?>()  // LiveData for validation error messages.
    var isSignUpSuccess = MutableLiveData<Boolean>()  // LiveData indicating the success of the sign-up process.

    /**
     * Initiates the sign-up process by validating user input and calling the sign-up API.
     */
    fun callSignUpApi() {
        if (!validateFirstName()) return
        if (!validateLastName()) return
        if (!validateEmail()) return
        if (!validatePhoneNumber()) return
        if (!validatePassword()) return

        if (navigator!!.checkInternet(tryAgain = {
                callSignUpApi()
            })) {
            launchAsync({ signUpUseCase.signup(getRequestParams()) }, onSuccess = {
                isSignUpSuccess.postValue(true)
            }, {
            }, true, navigator!!)
        }
    }

    /**
     * Validates the first name input.
     * @return True if validation passes, false otherwise.
     */
    private fun validateFirstName(): Boolean {
        return when (val result = nameValidation.validateFirstName(signUp.value?.firstName)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Validates the last name input.
     * @return True if validation passes, false otherwise.
     */
    private fun validateLastName(): Boolean {
        return when (val result = nameValidation.validateLastName(signUp.value?.lastName)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Validates the email input.
     * @return True if validation passes, false otherwise.
     */
    private fun validateEmail(): Boolean {
        return when (val result = emailValidation.validateEmail(signUp.value?.email)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Validates the phone number input.
     * @return True if validation passes, false otherwise.
     */
    private fun validatePhoneNumber(): Boolean {
        return when (val result = phoneNumberValidation.validatePhoneNumber(signUp.value?.phoneNumber)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Validates the password input.
     * @return True if validation passes, false otherwise.
     */
    private fun validatePassword(): Boolean {
        return when (val result = passwordValidation.validatePassword(signUp.value?.password)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Constructs the request parameters for the sign-up API call.
     * @return HashMap containing the sign-up request parameters.
     */
    private fun getRequestParams(): HashMap<String, Any?> {
        val param: HashMap<String, Any?> = HashMap()
        param[Constants.API_KEY_FIRST_NAME] = signUp.value!!.firstName!!.trim()
        param[Constants.API_KEY_LAST_NAME] = signUp.value!!.lastName!!.trim()
        param[Constants.API_KEY_PHONE_NUMBER] = signUp.value!!.phoneNumber!!.trim()
        param[Constants.API_KEY_EMAIL] = signUp.value!!.email!!.trim()
        param[Constants.API_KEY_PASSWORD] = signUp.value!!.password!!.trim()
        param[Constants.API_KEY_CONFIRM_PASSWORD] = signUp.value!!.password!!.trim()
        return param
    }
}