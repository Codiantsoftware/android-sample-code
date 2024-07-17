package com.example.myapplication.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.example.myapplication.R
import java.util.Objects


object DialogUtil {

    private var dialog : Dialog?  =null
    var checkMsg= ""

    /**
     * Show a dialog indicating no internet connection and provide an option to retry.
     * @param context The context from which the dialog is launched.
     * @param onClick Callback to be executed when the confirm button is clicked.
     */
    fun showDialogNoInternetData(
        context: Context,
        onClick: () -> Unit
    ) {
        val dialog1 = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(dialog1.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setContentView(R.layout.no_internet_data_layout)
        dialog1.setCancelable(false)

        val confirmButton = dialog1.findViewById<Button>(R.id.confirm_button)

        DataBinding.onSingleClick(confirmButton) {
            onClick.invoke()
            dialog1.dismiss()
        }
        dialog1.show()
    }

    @Suppress("unused")
    fun hideDialog(){
        if(dialog !=null){
            dialog!!.dismiss()
        }
    }


    /**
     * Show an alert dialog with customizable options and actions.
     * @param context The context from which the dialog is launched.
     * @param alertTitle The title of the dialog.
     * @param alertMsg The message content of the dialog.
     * @param leftButtonTitle The title of the left button (optional).
     * @param rightButtonTitle The title of the right button (optional).
     * @param bottomTextTitle The title of the bottom text (optional).
     * @param showCross Indicates whether to show a cross icon (optional).
     * @param imageResId The resource ID of the image to be displayed (optional).
     * @param onClickLeftButton Callback to be executed when the left button is clicked (optional).
     * @param onClickRightButton Callback to be executed when the right button is clicked (optional).
     * @param onClickBottomText Callback to be executed when the bottom text is clicked (optional).
     * @param onCrossClick Callback to be executed when the cross icon is clicked (optional).
     * @param gravityStatus Indicates whether to set gravity to start for the message text (optional).
     */
    fun showAlertDialog(context: Context,
                        alertTitle: String?= null,
                        alertMsg: String,
                        leftButtonTitle: String? = null,
                        rightButtonTitle: String? = null,
                        bottomTextTitle: String? = null,
                        showCross:Boolean = false,
                        imageResId: Int?=null,
                        onClickLeftButton: () -> Unit={},
                        onClickRightButton: () -> Unit={},
                        onClickBottomText: () -> Unit={},
                        onCrossClick: () -> Unit={},
                        gravityStatus: Boolean = false,
    ){
        val dialogCommon = Dialog(context)
        dialogCommon.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCommon.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(dialogCommon.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCommon.setContentView(R.layout.common_dialog_three_buttons)
        dialogCommon.setCancelable(false)
        dialogCommon.window!!.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        val crossIV = dialogCommon.findViewById<AppCompatImageView>(R.id.crossIV)
        val image = dialogCommon.findViewById<AppCompatImageView>(R.id.image)
        val titleText = dialogCommon.findViewById<AppCompatTextView>(R.id.titleText)
        val messageTV = dialogCommon.findViewById<AppCompatTextView>(R.id.messageTV)
        val leftButton = dialogCommon.findViewById<AppCompatButton>(R.id.noButton)
        val rightButton = dialogCommon.findViewById<AppCompatButton>(R.id.yesButton)
        val bottomText = dialogCommon.findViewById<AppCompatTextView>(R.id.continueText)

        if(gravityStatus)
        {
            messageTV.gravity = Gravity.START
        }


        if(!alertTitle.isStringNullOrBlank()){
            titleText.text = alertTitle
        }else{
            titleText.text = context.resources.getString(R.string.app_name)
        }

        if(!alertMsg.isStringNullOrBlank()){
            messageTV.text = alertMsg
        }
        if(!leftButtonTitle.isStringNullOrBlank()){
            leftButton.text = leftButtonTitle
            leftButton.show()
        }else{
            leftButton.hide()

        }
        if(!rightButtonTitle.isStringNullOrBlank()){
            rightButton.text = rightButtonTitle
            rightButton.show()
        }else{
            rightButton.hide()
        }

        if(!bottomTextTitle.isStringNullOrBlank()){
            bottomText.text = bottomTextTitle
            bottomText.show()
        }else{

            bottomText.hide()
        }


        if(imageResId != null && imageResId != 0){
            image.updateLayoutParams<ConstraintLayout.LayoutParams> {topToTop = ConstraintSet.PARENT_ID}
            image.setImageResource(imageResId)
            image.show()
        }else{
            image.hide()
        }

        if (showCross){
            crossIV.show()
        }else{
            crossIV.hide()
        }


        DataBinding.onSingleClick(crossIV) {
            onCrossClick.invoke()
            dialogCommon.dismiss()
            checkMsg = ""
        }
        DataBinding.onSingleClick(leftButton) {
            onClickLeftButton.invoke()
            dialogCommon.dismiss()
            checkMsg = ""
        }
        DataBinding.onSingleClick(rightButton) {
            onClickRightButton.invoke()
            dialogCommon.dismiss()
            checkMsg = ""
        }
        DataBinding.onSingleClick(bottomText) {
            onClickBottomText.invoke()
            dialogCommon.dismiss()
            checkMsg = ""
        }
        if(!dialogCommon.isShowing && checkMsg!=alertMsg){
            checkMsg = alertMsg
            dialogCommon.show()
        }
    }


}