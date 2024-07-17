package com.example.myapplication.util

import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter

@Suppress("unused")
object DataBinding {

    @JvmStatic
    @BindingAdapter("setEnabled")
    fun isEnabled(view: EditText, str: String?) {
        view.isEnabled = str.isStringNullOrBlank()
    }

    @JvmStatic
    @BindingAdapter("onSingleClick")
    fun onSingleClick(view: View?, onClick: () -> Unit) {
        view?.setOnClickListener(object : OnSingleClickListener({
            onClick.invoke()
        }){})
    }

    @BindingAdapter("onSingleClickR")
    fun View.onSingleClickR(onClick: () -> Unit) {
        setOnClickListener(object : OnSingleClickListener({
            onClick.invoke()
        }){})
    }

    @Suppress("DEPRECATION")
    @JvmStatic
    @BindingAdapter("removeHtmlTags")
    fun removeHtmlTags(view: TextView, str: String?) {
        view.text = Html.fromHtml(str).toString()
    }
}