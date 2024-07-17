package com.example.myapplication.presentation.activities.signup

import com.example.myapplication.presentation.base.CommonNavigator


/**
 * Navigator interface for sign-up related actions in the UI.
 * Extends the CommonNavigator interface for common UI navigation actions.
 */
interface SignUpNavigator: CommonNavigator {
    fun navigateToLoginScreen()
    fun onSignUpClick()
    fun onGoogleSignUpClick()
}