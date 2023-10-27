package com.firstdueapplication.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    fun hideKeyboard(context: Context, view:View){
        view?.let {
            val inputMethodManager =
                context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }
}