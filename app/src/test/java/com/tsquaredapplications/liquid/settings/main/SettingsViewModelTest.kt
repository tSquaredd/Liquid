package com.tsquaredapplications.liquid.settings.main

import com.tsquaredapplications.liquid.common.BaseTest
import com.tsquaredapplications.liquid.settings.resources.SettingsResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_DAILY_GOAL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WEIGHT
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SettingsViewModelTest : BaseTest() {

    lateinit var viewModel: SettingsViewModel

    private val userInformation = mockUserInformation()
    private val resourceWrapper = mockk<SettingsResourceWrapper> {
        every { dailyGoal } returns DAILY_GOAL
        every { weight } returns WEIGHT
        every { preferredMeasurement } returns PREFERRED_MEASUREMENT
        every { allowReminders } returns ALLOW_REMINDERS
        every { rateThisApp } returns RATE_THIS_APP
        every { lbs } returns LBS
        every { on } returns ON
    }


    @BeforeEach
    fun beforeEach() {
        viewModel = SettingsViewModel(userInformation, resourceWrapper)
    }

    @Test
    fun `return correct list of settings items`() {
        with(viewModel.getSettingsItems()) {
            assertEquals(5, size)
            with(get(0).setting) {
                assertEquals(DAILY_GOAL, name)
                assertEquals("$MOCK_DAILY_GOAL$MOCK_UNIT_PREF", value)
                assertEquals(SettingType.DailyGoal, settingType)
            }
            with(get(1).setting) {
                assertEquals(WEIGHT, name)
                assertEquals("$MOCK_WEIGHT $LBS", value)
                assertEquals(SettingType.Weight, settingType)
            }
            with(get(2).setting) {
                assertEquals(PREFERRED_MEASUREMENT, name)
                assertEquals("$MOCK_UNIT_PREF", value)
                assertEquals(SettingType.UnitPreference, settingType)
            }
            with(get(3).setting) {
                assertEquals(ALLOW_REMINDERS, name)
                assertEquals(ON, value)
                assertEquals(SettingType.Notifications, settingType)
            }
            with(get(4).setting) {
                assertEquals(RATE_THIS_APP, name)
                assertEquals("", value)
                assertEquals(SettingType.RateThisApp, settingType)
            }
        }
    }

    companion object {
        private const val DAILY_GOAL = "DAILY_GOAL"
        private const val WEIGHT = "WEIGHT"
        private const val PREFERRED_MEASUREMENT = "PREFERRED_MEASUREMENT"
        private const val ALLOW_REMINDERS = "ALLOW_REMINDERS"
        private const val RATE_THIS_APP = "RATE_THIS_APP"
        private const val LBS = "LBS"
        private const val ON = "ON"
    }
}