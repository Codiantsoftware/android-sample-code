package com.example.myapplication.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.myapplication.R.id
import com.example.myapplication.R.layout
import com.example.myapplication.R.style


/**
 * The LoaderDialog class represents a custom dialog for displaying a loading indicator.
 */
class LoaderDialog {

    /**
     * The dialog instance used to display the loading indicator.
     */
    private var dialog: Dialog? = null

    /**
     * Shows the loading dialog with an optional title.
     * @param context The context in which the dialog will be displayed.
     * @param title The title to be displayed in the dialog (can be null).
     * @return The dialog instance.
     */
    @SuppressLint("InflateParams")
    fun show(context: Context, title: CharSequence?): Dialog? {
        val layoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(layout.layout_loader_dialog, null)
        if (title != null) {
            val tv = view.findViewById<View>(id.id_title) as TextView
            tv.text = title
        }
        dialog = Dialog(context, style.CustomProgressDialog)
        dialog!!.setContentView(view)
        dialog!!.setCancelable(false)
        dialog!!.show()
        return dialog
    }

    /**
     * Dismisses the loading dialog if it is currently showing.
     */
    fun dismiss() {
        dialog?.dismiss()
    }
}