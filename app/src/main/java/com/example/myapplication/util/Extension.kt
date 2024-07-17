package com.example.myapplication.util

import android.content.res.Resources
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * Extension function to observe LiveData from within a LifecycleOwner (Activity or Fragment).
 * @param liveData The LiveData object to observe.
 * @param body The action to be performed with the observed data.
 */
@Suppress("unused")
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))


/**
 * Extension function to convert an integer value to pixels based on the device's screen density.
 * @return The converted pixel value.
 */
@Suppress("unused")
fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Checks if a string is null or blank.
 * @return True if the string is null or blank, false otherwise.
 */
fun String?.isStringNullOrBlank(): Boolean {

    try {
        if (this == null) {
            return true
        } else if (this == "null" || this == "" || this.isEmpty() || this.equals(
                "null",
                ignoreCase = true
            ) || this.equals("", ignoreCase = true)
        ) {
            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}

/**
 * Checks if a string is a valid email address.
 * @return True if the string is a valid email address, false otherwise.
 */
fun String.isInvalidEmail(): Boolean {
    return !Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun View.show(){
    this.visibility = VISIBLE
}

fun View.hide(){
    this.visibility = GONE
}

@Suppress("unused")
fun View.invisible(){
    this.visibility = INVISIBLE
}

