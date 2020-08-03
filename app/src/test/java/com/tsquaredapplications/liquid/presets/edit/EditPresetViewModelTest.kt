package com.tsquaredapplications.liquid.presets.edit

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.AmountInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Deleted
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.IconUpdated
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Initialized
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.NameInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Updated
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_AMOUNT_STRING
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_NAME
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterPreset
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockPresetRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterPresetDataWrapper
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EditPresetViewModelTest : BaseCoroutineViewModelTest<EditPresetState>() {

    lateinit var viewModel: EditPresetViewModel

    private val userInformation = mockUserInformation()
    private val presetRepository = mockPresetRepository(withPresets = true)
    private val entryRepository = mockEntryRepository()
    private val presetDataWrapper = mockWaterPresetDataWrapper()
    private val resourceWrapper = mockk<EditPresetResourceWrapper> {
        every { amountErrorMessage } returns AMOUNT_ERROR_MESSAGE
        every { nameErrorMessage } returns NAME_ERROR_MESSAGE
        every { deleteFailureMessage } returns DELETE_FAILURE_MESSAGE
        every { updateFailureMessage } returns UPDATE_FAILURE_MESSAGE
        every { failureDismissText } returns FAILURE_DISMISS_TEXT
    }

    @BeforeEach
    fun beforeEach() {
        viewModel =
            EditPresetViewModel(
                userInformation,
                presetRepository,
                entryRepository,
                resourceWrapper
            ).apply {
                stateLiveData.observeForever(stateObserver)
            }
    }

    @Test
    fun `given viewModel started set state to Initialized`() {
        viewModel.start(presetDataWrapper)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue(stateList.first() is Initialized)
        with(stateList.first() as Initialized) {
            assertEquals(MOCK_WATER_PRESET_NAME, name)
            assertEquals(MOCK_WATER_PRESET_LARGE_ICON_RESOURCE, iconResource)
            assertEquals(MOCK_WATER_PRESET_AMOUNT_STRING, amountText)
            assertEquals(MOCK_UNIT_PREF.toString(), amountUnitHint)
        }
    }

    @Test
    fun `given delete is clicked, then set state to Deleted`() {
        with(viewModel) {
            start(presetDataWrapper)
            deletePreset()
        }

        val presetRepositorySlot = slot<Preset>()
        coVerify(exactly = 1) { presetRepository.delete(capture(presetRepositorySlot)) }
        presetRepositorySlot.captured.assertWaterPreset()

        val entryRepositorySlot = slot<Preset>()
        coVerify(exactly = 1) { entryRepository.presetRemoval(capture(entryRepositorySlot)) }
        entryRepositorySlot.captured.assertWaterPreset()

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            Initialized::class,
            Deleted::class
        )
    }

    @Test
    fun `given invalid amount and name set invalid states`() {
        with(viewModel) {
            start(presetDataWrapper)
            onNameChanged("")
            onAmountChanged("0")
            updatePreset()
        }

        verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            Initialized::class,
            AmountInvalid::class,
            NameInvalid::class
        )
    }

    @Test
    fun `given valid new name and amount update preset and set state to Updated`() {
        val newName = "New Name"
        val newAmount = "16"
        val newIconId = 123
        val newIcon = mockk<Icon>(relaxed = true) {
            every { iconUid } returns newIconId
        }

        with(viewModel) {
            start(presetDataWrapper)
            onNameChanged(newName)
            onAmountChanged(newAmount)
            presetIconSelected(newIcon)
            updatePreset()
        }

        verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            Initialized::class,
            IconUpdated::class,
            Updated::class
        )

        coVerify(exactly = 1) { presetRepository.update(any()) }
        with(presetDataWrapper.preset) {
            verify { name = newName }
            verify { amount = newAmount.toDouble() }
            verify { iconUid = newIconId }
        }
    }

    companion object {
        const val AMOUNT_ERROR_MESSAGE = "AMOUNT_ERROR_MESSAGE"
        const val NAME_ERROR_MESSAGE = "NAME_ERROR_MESSAGE"
        const val DELETE_FAILURE_MESSAGE = "DELETE_FAILURE_MESSAGE"
        const val UPDATE_FAILURE_MESSAGE = "UPDATE_FAILURE_MESSAGE"
        const val FAILURE_DISMISS_TEXT = "FAILURE_DISMISS_TEXT"
    }
}