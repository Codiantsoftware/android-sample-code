package com.example.myapplication.presentation.activities.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.datalayer.entities.UserDto
import com.example.myapplication.domainlayer.model.UserModel
import com.example.myapplication.local.AppPreference
import com.example.myapplication.local.PreferenceKeys
import com.example.myapplication.presentation.activities.home.HomeActivity
import com.example.myapplication.presentation.activities.signup.SignUpActivity
import com.example.myapplication.presentation.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * LoginActivity represents the screen where users can log in to the application.
 *
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    //Provides the binding variable for the ViewModel.
    override val bindingVariable: Int
        get() = BR.loginVM

    //Provides the layout resource ID for the activity.
    override val layoutId: Int
        get() = R.layout.activity_login

    //Provides the ViewModel instance for the activity using viewModels delegate.
    override val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var gso: GoogleSignInOptions

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val resultGoogleSignIn =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                } catch (e: ApiException) {
                    showSnackBar("Error in Google login")
                    viewModel.googleSignOut()
                    hideProgress()
                }
            }
        }

    /**
     * Initializes the activity when created.
     * Checks if the user is already logged in and redirects to the home screen if so.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (AppPreference.get<UserModel>(PreferenceKeys.USER_DATA) != null) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }
    }

    /**
     * Initializes the view model and observes changes in user data and validation errors.
     */
    override fun init() {
        viewModel.navigator = this
        viewModel.user.value = UserDto()

        viewModel.userModel.observe(this) {
            hideProgress()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }

        viewModel.validationError.observe(this) {
            hideProgress()
            showSnackBar(it ?: getString(R.string.error_generic))
        }

    }

    /**
     * Handles the login button click event.
     * Initiates the login process by calling the login API.
     */
    override fun onLoginClick() {
        hideKeyboard()
        viewModel.callLoginAPI()
    }

   /**
    * Handles the sign-up button click event.
    * Redirects the user to the sign-up screen.
    */
    override fun onSignUpClick() {
        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
    }

    /**
     * Handles the "forgot password" click event.
     * Displays a message indicating that the feature is not available.
     */
    override fun onForgotPasswordClick() {
        showSnackBar(getString(R.string.feature_not_available))
    }

    /**
     * Handles the Google sign-up click event.
     * Displays a message indicating that the feature is not available.
     */
    override fun onGoogleSignUpClick() {
//        showSnackBar(getString(R.string.feature_not_available))
        showProgress()
        val signInIntent = googleSignInClient.signInIntent
        resultGoogleSignIn.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        CoroutineScope(Dispatchers.IO).launch {
            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            viewModel.loginViaGoogle(credential)
        }
    }

    /**
     * Performs additional actions when the activity resumes.
     * Clears the email and password fields.
     */
    override fun onResume() {
        super.onResume()
        viewDataBinding?.etEmail?.setText("")
        viewDataBinding?.etPassword?.setText("")
    }

}