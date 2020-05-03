package com.tsquaredapplications.liquid.ext

import java.util.regex.Pattern

fun CharSequence.validateEmail(): Boolean {
    val pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher = pattern.matcher(this)

    return this.isNotEmpty() && matcher.matches()
}

const val EMAIL_PATTERN =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"