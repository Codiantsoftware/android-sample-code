package com.example.myapplication.presentation.activities.home

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.datalayer.mapperes.UserMapper
import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.domainlayer.usecase.GetUserUseCase
import com.example.myapplication.domainlayer.usecase.LogoutUseCase
import com.example.myapplication.domainlayer.usecase.SaveUserUseCase
import com.example.myapplication.domainlayer.usecase.UserDetailUseCase
import com.example.myapplication.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the HomeActivity responsible for managing user data and interactions.
 * @property userDetailUseCase The use case for retrieving user details.
 * @property saveUserUseCase The use case for saving user data.
 * @property getUserUseCase The use case for retrieving user data.
 * @property userMapper The mapper for mapping user data between layers.
 * @property logoutUseCase The use case for logging out the user.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDetailUseCase: UserDetailUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userMapper: UserMapper,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<HomeNavigator>() {

    //MutableLiveData holding the current user data.
    var userModel = MutableLiveData<UserModel?>()
    // MutableLiveData indicating whether logout operation was successful.
    var isLogOutSuccess = MutableLiveData<Boolean>()

    /**
     * Calls the user detail API to fetch and update user data.
     */
    fun callUserDetailAPI() {
        if (navigator!!.checkInternet(tryAgain = {
                callUserDetailAPI()
            })) {
            launchAsync({ userDetailUseCase.getUserDetail() }, onSuccess = {
                val user = userMapper.map(it.userDto)
                val token = getUserUseCase.getUser()?.token
                user.token = token
                saveUserUseCase.saveUser(user)
                userModel.postValue(getUserUseCase.getUser())
            }, {
            }, true, navigator!!)
        }
    }

    /**
     * Calls the logout API to log out the user.
     */
    fun callLogoutAPI() {
        if (navigator!!.checkInternet(tryAgain = {
                callLogoutAPI()
            })) {
            launchAsync({ logoutUseCase.logout() }, onSuccess = {
                if (it.success) {
                    saveUserUseCase.logout()
                    isLogOutSuccess.postValue(true)
                }
            }, {
            }, true, navigator!!)
        }
    }
}