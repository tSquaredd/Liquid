package com.tsquaredapplications.liquid.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.ext.InstantExecutorExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
abstract class BaseCoroutineViewModelTest<T> : BaseTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    var stateList = mutableListOf<T>()
    val stateObserver = mockk<Observer<T>>(relaxed = true)

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @BeforeAll
    fun _beforeAll() {
        Dispatchers.setMain(testDispatcher)
        beforeAll()
    }

    @ExperimentalCoroutinesApi
    @AfterAll
    fun _afterAll() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        afterAll()
    }

    open fun beforeAll() {}
    open fun afterAll() {}
}