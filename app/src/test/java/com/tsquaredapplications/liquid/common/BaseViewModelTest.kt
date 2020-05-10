package com.tsquaredapplications.liquid.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tsquaredapplications.liquid.ext.InstantExecutorExtension
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
open class BaseViewModelTest : BaseTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}