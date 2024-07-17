package com.example.myapplication.util

import android.os.SystemClock
import android.view.View

open class OnSingleClickListener(private val onClicked: () -> Unit) : View.OnClickListener {
    /**
     *click
     */
    private var mLastClickTime: Long = 0

    /**
     * click
     * @param v The view that was clicked.
     */


    override fun onClick(v: View?) {
        val currentClickTime: Long = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return
        }else {
            mLastClickTime = currentClickTime
            onClicked.invoke()
            return
        }

    }

    companion object {
        /**
         * click
         */
        private const val MIN_CLICK_INTERVAL: Long = 1000
    }
}