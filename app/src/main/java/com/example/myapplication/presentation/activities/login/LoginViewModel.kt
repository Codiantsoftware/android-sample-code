package com.example.myapplication.presentation.activities.login

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.datalayer.entities.UserDto
import com.example.myapplication.datalayer.mapperes.FirebaseUserToUserModelMapper
import com.example.myapplication.datalayer.mapperes.UserMapper
import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.domainlayer.usecase.GetUserUseCase
import com.example.myapplication.domainlayer.usecase.GoogleLoginUseCase
import com.example.myapplication.domainlayer.usecase.LoginUseCase
import com.example.myapplication.domainlayer.usecase.SaveUserUseCase
import com.example.myapplication.presentation.base.BaseViewModel
import com.example.myapplication.util.Constants
import com.example.myapplication.util.validation.EmailValidation
import com.example.myapplication.util.validation.PasswordValidation
import com.example.myapplication.util.validation.ValidationResult
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * LoginViewModel handles the logic for the login functionality in the application.
 * It manages user input validation, login API calls, and saving user data.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userMapper: UserMapper,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val firebaseUserToUserModelMapper: FirebaseUserToUserModelMapper
) : BaseViewModel<LoginNavigator>() {

    // LiveData for holding user input data
    var user = MutableLiveData<UserDto?>()
    // LiveData for holding mapped UserModel data
    var userModel = MutableLiveData<UserModel?>()
    // LiveData for holding validation error messages
    var validationError = MutableLiveData<String?>()

    /**
     * Initiates the login process by validating user input, checking internet connection,
     * and launching the login API call.
     */
    fun callLoginAPI() {
        if (!validateUserName()) return
        if (!validatePassword()) return
        if (navigator!!.checkInternet(tryAgain = {
                callLoginAPI()
            })) {
            launchAsync({ loginUseCase.login(getRequestParams()) }, onSuccess = {
                saveUserUseCase.saveUser(userMapper.map(it.userDto))
                userModel.postValue(getUserUseCase.getUser())
            }, {
            }, true, navigator!!)
        }
    }

    /**
     * Validates the email address entered by the user.
     * Returns true if the email is valid, otherwise sets the validation error LiveData and returns false.
     */
    private fun validateUserName(): Boolean {
        return when (val result = emailValidation.validateEmail(user.value?.email)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Validates the password entered by the user.
     * Returns true if the password is valid, otherwise sets the validation error LiveData and returns false.
     */
    private fun validatePassword(): Boolean {
        return when (val result = passwordValidation.validatePassword(user.value?.password)) {
            is ValidationResult.Error -> {
                validationError.postValue(result.message)
                false
            }

            else -> true
        }
    }

    /**
     * Constructs and returns the parameters required for the login API call.
     */
    private fun getRequestParams(): HashMap<String, Any?> {
        val param: HashMap<String, Any?> = HashMap()
        param[Constants.API_KEY_EMAIL_MOBILE] = user.value!!.email!!.trim()
        param[Constants.API_KEY_PASSWORD] = user.value!!.password!!.trim()
        param[Constants.API_KEY_DEVICE_TYPE] = Constants.DEVICE_TYPE
        return param
    }

    /**
     * Manage login using Google
     *
     * @param googleCredential
     */
    suspend fun loginViaGoogle(googleCredential: AuthCredential) {
        val response = googleLoginUseCase.loginViaGoogle(googleCredential)
        if (response.failure != null) {
           validationError.postValue(response.failure.message)
        } else {
            saveUserUseCase.saveUser(firebaseUserToUserModelMapper.map(response.firebaseUser!!))
            userModel.postValue(getUserUseCase.getUser())
            googleSignOut()
        }
    }

    fun googleSignOut() {
        googleLoginUseCase.logout()
    }
}