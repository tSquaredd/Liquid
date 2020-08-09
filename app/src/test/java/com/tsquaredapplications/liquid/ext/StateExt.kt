package com.tsquaredapplications.liquid.ext

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.reflect.KClass

fun <T> MutableList<T>.assertStateOrder(vararg states: KClass<out Any>) {
    states.forEachIndexed { index, clazz ->
        assertTrue { clazz.isInstance(this[index]) }
    }
}