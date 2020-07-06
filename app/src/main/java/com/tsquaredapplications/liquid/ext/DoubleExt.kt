package com.tsquaredapplications.liquid.ext

import java.text.DecimalFormat

fun Double.toTwoDigitDecimalString(): String = DecimalFormat("#.00").format(this)