package com.tsquaredapplications.liquid.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.ext.InstantExecutorExtension
import io.mockk.mockk
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
open class BaseViewModelTest<T> : BaseTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    var stateList = mutableListOf<T>()
    val stateObserver = mockk<Observer<T>>(relaxed = true)
}