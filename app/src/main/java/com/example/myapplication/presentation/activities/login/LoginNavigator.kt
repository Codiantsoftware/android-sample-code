package com.example.myapplication.presentation.activities.login

import com.example.myapplication.presentation.base.CommonNavigator


/**
 * LoginNavigator interface defines the navigation actions available from the login screen.
 * It extends the CommonNavigator interface for common navigation actions.
 */
interface LoginNavigator: CommonNavigator {
    fun onLoginClick()
    fun onSignUpClick()
    fun onForgotPasswordClick()
    fun onGoogleSignUpClick()
}