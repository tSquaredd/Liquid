package com.tsquaredapplications.liquid.settings.notifications

import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_ENABLED
import com.tsquaredapplications.liquid.test.util.mocks.assertEndTime
import com.tsquaredapplications.liquid.test.util.mocks.assertStartTime
import com.tsquaredapplications.liquid.test.util.mocks.mockNotificationManager
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockUserManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NotificationsSettingsViewModelTest :
    BaseViewModelTest<NotificationsSettingsState>() {

    lateinit var viewModel: NotificationsSettingsViewModel

    private val userInformation = mockUserInformation()
    private val userManager = mockUserManager()
    private val notificationManager = mockNotificationManager()
    private val resourceWrapper = mockk<NotificationSettingsResourceWrapper> {
        every { am } returns AM
        every { pm } returns PM
    }

    @BeforeEach
    fun beforeEach() {
        viewModel = NotificationsSettingsViewModel(
            userInformation,
            userManager,
            notificationManager,
            resourceWrapper
        ).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `given viewModel started set state to Initialized`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is NotificationsSettingsState.Initialized }
        with(stateList.first() as NotificationsSettingsState.Initialized) {
            assertEquals(MOCK_ENABLED, enabled)
            startTime.assertStartTime()
            endTime.assertEndTime()
            assertEquals("9:00 $AM", startTimeString)
            assertEquals("9:00 $PM", endTimeString)

            with(hourInterval) {
                assertEquals(12, maxInterval)
                assertEquals(2, currentSelection)
            }

            with(minuteInterval) {
                assertEquals("00", options[0])
                assertEquals("30", options[1])
                assertEquals(0, currentSelectionIndex)
            }
        }
    }

    companion object {
        const val AM = "AM"
        const val PM = "PM"
    }
}