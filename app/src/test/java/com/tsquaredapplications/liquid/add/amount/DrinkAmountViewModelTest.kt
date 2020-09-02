package com.tsquaredapplications.liquid.add.amount

import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.UpdateDateString
import com.tsquaredapplications.liquid.add.amount.resources.DrinkAmountResourceWrapper
import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterDrinkType
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class DrinkAmountViewModelTest : BaseCoroutineViewModelTest<DrinkAmountState>() {

    lateinit var viewModel: DrinkAmountViewModel

    private val userInformation = mockUserInformation()
    private val entryRepository = mockEntryRepository()
    private val drinkAmountResourceWrapper = mockk<DrinkAmountResourceWrapper> {
        every { amountErrorMessage } returns AMOUNT_ERROR_MESSAGE
        every { getMonthDisplayName(any()) } returns MONTH_DISPLAY_NAME
    }
    private val waterDrinkType = mockWaterDrinkType()

    @BeforeEach
    fun beforeEach() {
        viewModel =
            DrinkAmountViewModel(userInformation, entryRepository, drinkAmountResourceWrapper)

        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `when view model is started set drink type and return init state with unit pref`() {
        viewModel.start(waterDrinkType)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }

        assertEquals(waterDrinkType, viewModel.drinkType)
        assertTrue(stateList.size == 1)
        assertTrue(stateList.first() is DrinkAmountState.Initialized)
        with(stateList.first() as DrinkAmountState.Initialized) {
            assertEquals(MOCK_UNIT_PREF.toString(), unitPreference)
        }
    }

    @Test
    fun `given date is changed, then update date in viewModel and send UpdateDateString state`() {
        val year = 3000
        val month = 1
        val day = 13

        viewModel.onDateSet(null, year, month, day)

        assertEquals(year, viewModel.calendar.get(Calendar.YEAR))
        assertEquals(month, viewModel.calendar.get(Calendar.MONTH))
        assertEquals(day, viewModel.calendar.get(Calendar.DAY_OF_MONTH))

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(UpdateDateString::class)
    }

    @Test
    fun `when amount updated to valid double update amount in viewModel`() {
        val amountString = "16"
        viewModel.onAmountChanged(amountString)
        assertEquals(16.0, viewModel.amount)
    }

    @Test
    fun `when amount updated with invalid double string mark amount as null`() {
        val amountString = "a"
        viewModel.onAmountChanged(amountString)
        assertNull(viewModel.amount)
    }

    @Test
    fun `when add drink clicked with null amount send invalid state`() {
        with(viewModel) {
            start(waterDrinkType)
            onAddClicked()
        }

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            DrinkAmountState.Initialized::class,
            DrinkAmountState.InvalidAmount::class
        )
        with(stateList[1] as DrinkAmountState.InvalidAmount) {
            assertEquals(AMOUNT_ERROR_MESSAGE, errorMessage)
        }
    }

    @Test
    fun `when add drink clicked with 0 amount send invalid state`() {
        with(viewModel) {
            start(waterDrinkType)
            onAmountChanged("0.0")
            onAddClicked()
        }

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            DrinkAmountState.Initialized::class,
            DrinkAmountState.InvalidAmount::class
        )
        with(stateList[1] as DrinkAmountState.InvalidAmount) {
            assertEquals(AMOUNT_ERROR_MESSAGE, errorMessage)
        }
    }

    @Test
    fun `when add drink clicked with non null, non zero amount add drink`() = runBlocking {
        with(viewModel) {
            start(waterDrinkType)
            onAmountChanged("16")
            onAddClicked()
        }

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            DrinkAmountState.Initialized::class,
            DrinkAmountState.DrinkAdded::class
        )

        val entrySlot = slot<Entry>()
        coVerify { entryRepository.insert(capture(entrySlot)) }
        with(entrySlot.captured) {
            assertEquals(16.0, amount)
            assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
        }
    }

    companion object {
        private const val AMOUNT_ERROR_MESSAGE = "AMOUNT_ERROR_MESSAGE"
        private const val MONTH_DISPLAY_NAME = "MONTH_DISPLAY_NAME"
    }
}