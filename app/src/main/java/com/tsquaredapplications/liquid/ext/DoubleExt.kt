package com.tsquaredapplications.liquid.ext

import java.text.DecimalFormat

fun Double.toTwoDigitDecimalString(): String = DecimalFormat("#.##").format(this)