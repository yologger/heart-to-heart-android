package com.yologger.presentation.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:errorText")
    fun setErrorText(textInputLayout: TextInputLayout, errorText: String) {
        textInputLayout.error = errorText
    }
}