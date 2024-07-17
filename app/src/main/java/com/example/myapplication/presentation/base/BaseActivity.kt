package com.example.myapplication.presentation.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.myapplication.R
import com.example.myapplication.datalayer.api.Failure
import com.example.myapplication.datalayer.api.NoInternetError
import com.example.myapplication.datalayer.api.ServerError
import com.example.myapplication.datalayer.api.TimeoutError
import com.example.myapplication.datalayer.api.UnknownHostError
import com.example.myapplication.local.AppPreference
import com.example.myapplication.presentation.activities.login.LoginActivity
import com.example.myapplication.util.Constants
import com.example.myapplication.util.DialogUtil
import com.example.myapplication.util.LoaderDialog
import com.example.myapplication.util.isStringNullOrBlank
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonParseException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

/**
 * Base activity class for all activities in the application.
 * Provides common functionality and utilities used across activities.
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseFragment.Callback,
    CommonNavigator {

    private var loaderDialog: LoaderDialog? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }


    override fun checkInternet(tryAgain: () -> Unit): Boolean {
        return if (checkIfInternetOn()) {
            true
        } else {
            showInternet(tryAgain)
        }
    }

    private fun showInternet(tryAgain: () -> Unit): Boolean {
        DialogUtil.showDialogNoInternetData(this, onClick = {
            tryAgain.invoke()
        })

        return false
    }


    override fun onResume() {
        super.onResume()
        DialogUtil.checkMsg = ""

    }


    override fun onPause() {
        super.onPause()


        onPauseState()
    }


    @Suppress("DEPRECATION")
    fun getCurrentActivityName(): String? {
        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val currentActivityName = am.getRunningTasks(1)[0].topActivity
        return currentActivityName?.className
    }


    override fun showAlertDialog(
        alertTitle: String?,
        alertMsg: String,
        leftButtonTitle: String?,
        rightButtonTitle: String?,
        bottomTextTitle: String?,
        showCross: Boolean,
        imageResId: Int?,
        onClickLeftButton: () -> Unit,
        onClickRightButton: () -> Unit,
        onClickBottomText: () -> Unit,
        onCrossClick: () -> Unit
    ) {
        DialogUtil.showAlertDialog(
            context = this,
            alertTitle = alertTitle,
            alertMsg = alertMsg,
            leftButtonTitle = leftButtonTitle,
            rightButtonTitle = rightButtonTitle,
            bottomTextTitle = bottomTextTitle,
            showCross = showCross,
            imageResId = imageResId,
            onClickLeftButton,
            onClickRightButton,
            onClickBottomText,
            onCrossClick,
        )
    }


    /**
     * When an activity first call or launched then this method
     * is responsible to create the activity.
     *
     * @param savedInstanceState load all data from savedInstanceState.
     */
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        super.onCreate(savedInstanceState)
        performDataBinding()
        viewDataBinding!!.lifecycleOwner = this
        loaderDialog = LoaderDialog()
        init()


    }


    /**
     * This this method is used to hide device keyboard.
     */
    open fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    /**
     * called when required permission is granted to notify in child class need to override this
     */
    protected open fun invokedPermissionGranted() {}

    /**
     * called when required permission is not or already granted to notify in child class need to override this
     */
    open fun invokedAlreadyPermissionGranted() {}

    /**
     * This this method is used to hide soft keyboard.
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    /**
     * This this method is used for data binding.
     */
    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }


    /**
     * This is BaseActivity override method.
     */
    override fun onDestroy() {
        hideProgress()
        super.onDestroy()
        hideKeyboard(this)
    }

    /**
     * This is BaseActivity override method for showing progress dialog.
     */
    override fun showProgress() {
        loaderDialog?.show(this, getString(R.string.please_wait))
    }


    /**
     * This method is used to hide progress.
     */
    override fun hideProgress() {
       loaderDialog?.dismiss()
    }

    /**
     * This method is used set root view visibility.
     *
     */
    override fun setRootViewVisibility() {}

    /**
     * This method is used get string resource from string.xml.
     */
    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    /**
     * This method is used get integer resource .
     */
    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    /**
     * This BaseActivity method is override method.
     */
    override fun onBackClick() {
        onBackPressedDispatcher.onBackPressed()
    }


    override fun handleApiResponseException(e: Exception) {
        when (e) {
            is HttpException -> {
                try {
                    handleAPIFailure(
                        ServerError(
                            data = null,
                            statusCode = e.code(),
                            message = "handleApiResponseException"
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }

            is SocketException -> {
                try {

                    handleAPIFailure(
                        NoInternetError(
                            statusCode = 0,
                            message = "something went wrong while connecting to server! Please try again later."
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }

            is SocketTimeoutException -> {

                try {
                    handleAPIFailure(
                        TimeoutError(
                            statusCode = 0,
                            message = "request time out"
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }

            is UnknownHostException -> {
                try {
                    handleAPIFailure(
                        UnknownHostError(
                            statusCode = 0,
                            message = "something went wrong while connecting to server! Please try again later."
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }

            is JsonParseException, is JSONException -> {
                try {
                    handleAPIFailure(
                        UnknownHostError(
                            statusCode = 0,
                            message = "something went wrong with server response."
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }

            else -> {
                try {
                    handleAPIFailure(
                        com.example.myapplication.datalayer.api.UnknownError
                            (
                            statusCode = 0,
                            message = "unknown"
                        )
                    )
                } catch (e: Exception) {
                    println(e.printStackTrace())
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun checkIfInternetOn(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }

    override fun handleAPIFailure(failure: Failure) {
        when (failure) {
            is TimeoutError -> {
                showAlertDialog(
                    alertTitle = "Alert",
                    alertMsg = "request_time_out",
                    rightButtonTitle = "Ok",
                    showCross = false,
                    onClickRightButton = {

                    })

            }

            is NoInternetError -> {
                showAlertDialog(
                    alertTitle = "Alert",
                    alertMsg = "check_internet_error_msg",
                    rightButtonTitle = "ok",
                    showCross = false,
                    onClickRightButton = {

                    })

            }

            is UnknownHostError -> {
                if (checkIfInternetOn())
                    showAlertDialog(
                        alertTitle = "alert",
                        alertMsg = "something_wrong_try_again",
                        rightButtonTitle = "Ok",
                        showCross = false,
                        onClickRightButton = {

                        })
                else
                    showAlertDialog(
                        alertTitle = "Alert",
                        alertMsg = "check_internet_error_msg",
                        rightButtonTitle = "Ok",
                        showCross = false,
                        onClickRightButton = {

                        })

            }

            else -> {
                checkServerError(failure as ServerError)
            }
        }
    }

    private fun checkServerError(serverError: ServerError) {
        when (serverError.statusCode) {
            401 -> {
                showAlertDialog(
                    alertTitle = "Alert",
                    alertMsg = "unauthorized_access",
                    rightButtonTitle = "Ok",
                    showCross = false,
                    onClickRightButton = {
                         AppPreference.clearSharedPreference()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    })

            }

            else -> {
                showSnackBar(serverError.message)

            }
        }
    }

    override fun handleApiResponseHandler(jsonObject: JSONObject, statusCode: Int) {
        val alertMsg: String
        when (statusCode) {
            400 -> {
                alertMsg = checkJsonErrorBody(jsonObject)!!
            }

            404 -> {
                alertMsg = "http_404_error"
            }

            402 -> {
                alertMsg = "http_402_error"
            }

            405 -> {
                alertMsg = "http_405_error"
            }

            406 -> {
                alertMsg = "http_406_error"
            }


            else -> {
                alertMsg = checkJsonErrorBody(jsonObject)!!
            }


        }
        showAlertDialog(
            alertTitle = "Alert",
            alertMsg = alertMsg,
            rightButtonTitle = "ok",
            showCross = false,
            onClickRightButton = {
                if (statusCode == 401) {
                    AppPreference.clearSharedPreference()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else if (statusCode == 426) {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }
            })
    }

    private fun checkJsonErrorBody(jsonObject: JSONObject): String? {
        try {
            return if (jsonObject.has(Constants.API_KEY_MESSAGE)) {
                return jsonObject[Constants.API_KEY_MESSAGE] as String?
            } else {
                jsonObject.optString(Constants.API_KEY_MESSAGE)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    override fun showToast(msg: String?) {
        if (!msg.isStringNullOrBlank()) {
            showAlertDialog(
                alertTitle = "alert",
                alertMsg = msg!!,
                rightButtonTitle = "Ok",
                showCross = false
            )
        }
    }

    fun showSnackBar(message: String) {
        val view = viewDataBinding!!.root
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarLayout: View = snackBar.view
        val textView =
            snackBarLayout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()

    }
}



