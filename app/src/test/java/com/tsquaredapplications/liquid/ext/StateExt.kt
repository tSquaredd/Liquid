package com.tsquaredapplications.liquid.ext

import org.junit.jupiter.api.Assertions

fun <T> MutableList<T>.assertOneState() {
    Assertions.assertEquals(1, size)
}