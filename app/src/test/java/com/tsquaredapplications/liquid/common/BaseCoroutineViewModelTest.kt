package com.tsquaredapplications.liquid.common

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.ext.CoroutineTestExtension
import com.tsquaredapplications.liquid.ext.InstantExecutorExtension
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(InstantExecutorExtension::class)
abstract class BaseCoroutineViewModelTest<T> : BaseTest() {
    var stateList = mutableListOf<T>()
    val stateObserver = mockk<Observer<T>>(relaxed = true)

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutineTestExtension(testDispatcher)

    @BeforeEach
    fun reset() {
        clearMocks(stateObserver)
        stateList.clear()
    }
}