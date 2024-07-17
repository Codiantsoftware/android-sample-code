package com.example.myapplication.presentation.activities.home

import com.example.myapplication.presentation.base.CommonNavigator


/**
 * HomeNavigator interface extends the CommonNavigator interface and adds a method specific to home navigation.
 */
interface HomeNavigator: CommonNavigator {

    /**
     * Callback method triggered when logout action is initiated.
     */
    fun onLogoutClick()
}