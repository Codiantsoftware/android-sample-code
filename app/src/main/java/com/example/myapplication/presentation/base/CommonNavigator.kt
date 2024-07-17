package com.example.myapplication.presentation.base


import com.example.myapplication.datalayer.api.Failure
import org.json.JSONObject

/**
 * CommonNavigator interface defines common navigation and UI-related methods to be implemented by activities or fragments.
 */
interface CommonNavigator {

    /**
     * Initializes the navigator.
     */
    fun init()

    /**
     * Shows a toast message.
     * @param msg The message to display.
     */
    fun showToast(msg: String?){}

    /**
     * Shows an alert dialog with customizable options.
     * @param alertTitle The title of the alert dialog.
     * @param alertMsg The message to display in the alert dialog.
     * @param leftButtonTitle The text to display on the left button of the alert dialog.
     * @param rightButtonTitle The text to display on the right button of the alert dialog.
     * @param bottomTextTitle The text to display at the bottom of the alert dialog.
     * @param showCross Flag indicating whether to show a cross icon in the alert dialog.
     * @param imageResId The resource ID of the image to display in the alert dialog.
     * @param onClickLeftButton Callback function for left button click event.
     * @param onClickRightButton Callback function for right button click event.
     * @param onClickBottomText Callback function for bottom text click event.
     * @param onCrossClick Callback function for cross icon click event.
     */
    fun showAlertDialog(
        alertTitle: String? = null,
        alertMsg: String,
        leftButtonTitle: String? = null,
        rightButtonTitle: String? = null,
        bottomTextTitle: String? = null,
        showCross: Boolean = false,
        imageResId: Int? = null,
        onClickLeftButton: () -> Unit = {},
        onClickRightButton: () -> Unit = {},
        onClickBottomText: () -> Unit = {},
        onCrossClick: () ->Unit = {}
    ){
        //
    }

    /**
     * Sets the visibility of the root view.
     */
    fun setRootViewVisibility()

    /**
     * Retrieves a string resource.
     * @param id The resource ID of the string.
     * @return The string resource.
     */
    fun getStringResource(id: Int): String

    /**
     * Retrieves an integer resource.
     * @param id The resource ID of the integer.
     * @return The integer resource.
     */
    fun getIntegerResource(id: Int): Int

    /**
     * Sets the status bar color.
     */
    fun setStatusBarColor() {}

    /**
     * Shows progress indicator.
     */
    fun showProgress()

    /**
     * Hides progress indicator.
     */
    fun hideProgress()

    /**
     * Callback for refresh action.
     */
    fun onRefresh(){}

    /**
     * Callback for pause state of the activity or fragment.
     */
    fun onPauseState(){}

    /**
     * Handles API response.
     * @param jsonObject The JSON object response.
     * @param statusCode The status code of the API response.
     */
    fun handleApiResponseHandler(jsonObject: JSONObject, statusCode: Int)

    /**
     * Handles API response exception.
     * @param e The exception object.
     */
    fun handleApiResponseException(e: Exception)

    /**
     * Callback for back button click.
     */
    fun onBackClick()

    /**
     * Checks internet connectivity.
     * @param tryAgain Callback function to retry when internet is not available.
     * @return True if internet is available, false otherwise.
     */
    fun checkInternet(tryAgain:() -> Unit={}) :Boolean

    /**
     * Handles API failure.
     * @param failure The failure object.
     */
    fun handleAPIFailure(failure: Failure){}
}