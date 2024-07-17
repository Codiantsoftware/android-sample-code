package com.example.myapplication.presentation.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response


/**
 * Base ViewModel class for all view models in the application.
 * Provides common functionality and utilities used across view models.
 */
abstract class BaseViewModel<N : Any> : ViewModel() {

    private val isLoading = ObservableBoolean(false)

    var navigator: N? = null

    private val _failure: MutableLiveData<Throwable> = MutableLiveData()

    val failure: LiveData<Throwable> get() = _failure

    /**
     * Sets the loading state.
     */
    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    /**
     * Launches a coroutine asynchronously.
     */
    inline fun <T> launchAsync(
        crossinline execute: suspend () -> Response<T>,
        crossinline onSuccess: (T) -> Unit,
        crossinline onFailure: (String) -> Unit = {},
        showProgress: Boolean = true,
        navigator: CommonNavigator
    ) {
        viewModelScope.launch {

            if (showProgress)
                navigator.showProgress()
            try {
                val response = execute()
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                    navigator.hideProgress()
                } else {
                    navigator.hideProgress()
                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    try {
                        onFailure(jsonObject.optString(Constants.API_KEY_MESSAGE))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onFailure("")
                    }
                    navigator.handleApiResponseHandler(
                        jsonObject, response.code()
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                navigator.handleApiResponseException(ex)
                navigator.hideProgress()
                onFailure("")
            } finally {
                navigator.hideProgress()
                onFailure("")
            }
        }
    }

    /**
     * Launches a coroutine with error handling.
     */
    protected fun launch(result: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                result()
            } catch (cause: Throwable) {
                _failure.postValue(cause)
            }
        }
    }

}