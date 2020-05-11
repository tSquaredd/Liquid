package com.tsquaredapplications.liquid.ext

import com.tsquaredapplications.liquid.login.common.PasswordValidation
import java.util.regex.Pattern

fun CharSequence.isValidEmail(): Boolean {
    val pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher = pattern.matcher(this)

    return this.isNotEmpty() && matcher.matches()
}

fun CharSequence.isValidPassword(): PasswordValidation {
    if (this.length < 6) return PasswordValidation.InvalidTooShort
    if (this.length > 30) return PasswordValidation.InvalidTooLong

    val pattern = Pattern.compile(UPPER_CASE_REGEX)
    val matcher = pattern.matcher(this)
    if (!matcher.matches()) return PasswordValidation.InvalidNoUpperCase

    return PasswordValidation.Valid
}

const val EMAIL_PATTERN =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"

const val UPPER_CASE_REGEX = ".*[A-Z].*"