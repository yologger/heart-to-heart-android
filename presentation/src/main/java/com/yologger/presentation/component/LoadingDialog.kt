package com.yologger.presentation.component

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.yologger.presentation.R

class LoadingDialog(private val context: Context) {

    private val dialog: Dialog by lazy { Dialog(context) }
    lateinit var textViewMessage: TextView

    fun show(message: String) {
        if (!dialog.isShowing) {
            // Remove title bar
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            // Setup layout
            dialog.setContentView(R.layout.dialog_loading)

            // Disable dismissing with back button
            dialog.setCancelable(false)

            // Disable dismissing with touching outside
            dialog.setCanceledOnTouchOutside(false)

            // Remove shadows
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            // Remove outside background color
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            textViewMessage = dialog.findViewById(R.id.dialog_button_textView)
            textViewMessage.text = message

            dialog.show()
        }
    }

    fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}