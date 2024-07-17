package com.example.myapplication.presentation.activities.home

import android.content.Intent
import androidx.activity.viewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.presentation.activities.login.LoginActivity
import com.example.myapplication.presentation.base.BaseActivity
import com.example.myapplication.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeActivity display user details.
 * It extends BaseActivity and implements HomeNavigator for handling home navigation.
 */
@AndroidEntryPoint
class HomeActivity: BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

   //Provides the binding variable for the ViewModel.
    override val bindingVariable: Int
        get() = BR.homeVM

    //Provides the layout resource ID for the activity.
    override val layoutId: Int
        get() = R.layout.activity_home

    //Provides the ViewModel instance for the activity using viewModels delegate.
    override val viewModel: HomeViewModel by viewModels()

    /**
     * Initializes the activity.
     * Sets up observers for logout success event and calls the user detail API.
     */
    override fun init() {
        viewModel.navigator = this
        viewModel.isLogOutSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.callUserDetailAPI()
    }

    /**
     * Handles logout click event.
     * Shows an alert dialog for logout confirmation and calls the logout API upon confirmation.
     */
    override fun onLogoutClick() {
        DialogUtil.showAlertDialog(this@HomeActivity,
            alertMsg = getString(R.string.logout_confirmation_message),
            leftButtonTitle = "No",
            rightButtonTitle = "Yes",
            onClickRightButton = {
                viewModel.callLogoutAPI()
            })
    }
}