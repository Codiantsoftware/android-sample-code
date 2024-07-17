package com.example.myapplication.presentation.activities.signup

import androidx.activity.viewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.example.myapplication.presentation.base.BaseActivity
import com.example.myapplication.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(), SignUpNavigator {

    //Provides the binding variable for the ViewModel.
    override val bindingVariable: Int
        get() = BR.signUpVM

    //Provides the layout resource ID for the activity.
    override val layoutId: Int
        get() = R.layout.activity_sign_up

    //Provides the ViewModel instance for the activity using viewModels delegate.
    override val viewModel: SignUpViewModel by viewModels()

    /**
     * Initializes the activity.
     * Sets up observers for view model LiveData.
     */
    override fun init() {
        viewModel.navigator = this
        viewModel.signUp.value = SignUp()

        viewModel.validationError.observe(this) {
            showSnackBar(it ?: getString(R.string.error_generic))
        }

        viewModel.isSignUpSuccess.observe(this) {
            if (it) {
                DialogUtil.showAlertDialog(this@SignUpActivity,
                    alertMsg = getString(R.string.sign_up_success_message),
                    rightButtonTitle = "OK",
                    onClickRightButton = {
                        navigateToLoginScreen()
                    })
            }
        }
    }

    /**
     * Handles the sign-up button click event.
     * Calls the corresponding method in the view model.
     */
    override fun onSignUpClick() {
        hideKeyboard()
        viewModel.callSignUpApi()
    }

    /**
     * Handles the Google sign-up button click event.
     * Displays a message indicating that the feature is not available.
     */
    override fun onGoogleSignUpClick() {
        showSnackBar(getString(R.string.feature_not_available))
    }

    /**
     * Navigates back to the login screen.
     */
    override fun navigateToLoginScreen() {
        onBackPressedDispatcher.onBackPressed()
    }
}