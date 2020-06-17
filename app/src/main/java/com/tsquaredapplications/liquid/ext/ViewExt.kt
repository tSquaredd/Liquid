package com.tsquaredapplications.liquid.ext

import android.view.View

fun View.setAsGone() {
    this.visibility = View.GONE
}

fun View.setAsVisible() {
    this.visibility = View.VISIBLE
}

fun View.setAsInvisible() {
    this.visibility = View.GONE
}

val keyboardHidingFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
    if (!hasFocus) view.context.hideKeyboardFrom(view.windowToken)
}