package com.tsquaredapplications.liquid.settings.notifications

import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.users.NotificationTime
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.common.notifications.NotificationManager
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.EndTimeUpdated
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.Finished
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.Initialized
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.NotificationsDisabled
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.NotificationsEnabled
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.StartTimeUpdated
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.UpdateButtonDisabled
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.UpdateButtonEnabled
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsState.UpdateIntervalOptions
import javax.inject.Inject

class NotificationsSettingsViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val userManager: UserManager,
    private val notificationManager: NotificationManager,
    private val resourceWrapper: NotificationSettingsResourceWrapper
) : BaseViewModel<NotificationsSettingsState>() {

    private var updatedNotificationsPreferences = userInformation.notifications.copy()

    fun start() {
        state.value = Initialized(
            updatedNotificationsPreferences.enabled,
            updatedNotificationsPreferences.startTime,
            buildTimeString(updatedNotificationsPreferences.startTime),
            updatedNotificationsPreferences.endTime,
            buildTimeString(updatedNotificationsPreferences.endTime),
            buildHourIntervalList(),
            buildMinIntervalList()
        )
    }

    private fun buildTimeString(notificationTime: NotificationTime): String {
        var minuteString = notificationTime.min.toString()
        if (minuteString.length == 1) minuteString = "${minuteString}0"

        val hourString =
            if (notificationTime.hour > 12) notificationTime.hour % 12 else notificationTime.hour
        val meridiem =
            if (notificationTime.hour > 12) resourceWrapper.pm else resourceWrapper.am

        return "$hourString:$minuteString $meridiem"
    }

    private fun buildHourIntervalList(): Pair<Int, Int> {
        val maxInterval =
            updatedNotificationsPreferences.endTime.hour - updatedNotificationsPreferences.startTime.hour
        return maxInterval to updatedNotificationsPreferences.intervalMins / 60
    }

    private fun buildMinIntervalList() =
        arrayOf("00", "30") to updatedNotificationsPreferences.intervalMins % 60

    fun onStartTimeChanged(hour: Int, min: Int) {
        state.value =
            StartTimeUpdated(buildTimeString(updatedNotificationsPreferences.startTime.apply {
                this.hour = hour
                this.min = min
            }))

        if (startTimeAfterEndTime()) {
            state.value =
                EndTimeUpdated(buildTimeString(updatedNotificationsPreferences.endTime.apply {
                    this.hour = updatedNotificationsPreferences.startTime.hour + 1
                }))
        }

        updateIntervals()
        determineUpdateButtonStatus()
    }

    fun onEndTimeChanged(hour: Int, min: Int) {
        state.value = EndTimeUpdated(buildTimeString(updatedNotificationsPreferences.endTime.apply {
            this.hour = hour
            this.min = min
        }))

        if (startTimeAfterEndTime()) {
            state.value =
                StartTimeUpdated(buildTimeString(updatedNotificationsPreferences.startTime.apply {
                    this.hour = updatedNotificationsPreferences.endTime.hour - 1
                }))
        }

        updateIntervals()
        determineUpdateButtonStatus()
    }

    private fun startTimeAfterEndTime() =
        updatedNotificationsPreferences.startTime.hour > updatedNotificationsPreferences.endTime.hour ||
                updatedNotificationsPreferences.startTime.hour == updatedNotificationsPreferences.endTime.hour && updatedNotificationsPreferences.startTime.min > updatedNotificationsPreferences.endTime.min

    fun allowRemindersChanged(checked: Boolean) {
        updatedNotificationsPreferences.enabled = checked
        state.value = if (checked) NotificationsEnabled else NotificationsDisabled
        determineUpdateButtonStatus()
    }

    fun selectedHourIntervalChanged(newVal: Int) {
        val minutes = updatedNotificationsPreferences.intervalMins % 60
        updatedNotificationsPreferences.intervalMins = (newVal * 60) + minutes
        determineUpdateButtonStatus()
    }

    fun selectedMinuteIntervalChanged(newVal: Int) {
        val hours = updatedNotificationsPreferences.intervalMins / 60
        updatedNotificationsPreferences.intervalMins = (hours * 60) + newVal
        determineUpdateButtonStatus()
    }

    private fun updateIntervals() {
        state.value = UpdateIntervalOptions(buildHourIntervalList(), buildMinIntervalList())
    }

    private fun determineUpdateButtonStatus() {
        state.value = if (userInformation.notifications != updatedNotificationsPreferences) {
            UpdateButtonEnabled
        } else {
            UpdateButtonDisabled
        }
    }

    fun onUpdateClicked() {
        userManager.setUser(userInformation.apply {
            notifications = updatedNotificationsPreferences
        })

        with(notificationManager) {
            disableNotification()
            enqueueNotifications(userInformation)
        }
        state.value = Finished
    }
}

sealed class NotificationsSettingsState {
    class Initialized(
        val enabled: Boolean,
        val startTime: NotificationTime,
        val startTimeString: String,
        val endTime: NotificationTime,
        val endTimeString: String,
        val hourIntervalOptions: Pair<Int, Int>,
        val minsIntervalOptions: Pair<Array<String>, Int>
    ) : NotificationsSettingsState()

    class StartTimeUpdated(val timeString: String) : NotificationsSettingsState()
    class EndTimeUpdated(val timeString: String) : NotificationsSettingsState()
    class UpdateIntervalOptions(
        val hourIntervalOptions: Pair<Int, Int>,
        val minsIntervalOptions: Pair<Array<String>, Int>
    ) : NotificationsSettingsState()

    object NotificationsEnabled : NotificationsSettingsState()
    object NotificationsDisabled : NotificationsSettingsState()
    object UpdateButtonEnabled : NotificationsSettingsState()
    object UpdateButtonDisabled : NotificationsSettingsState()
    object Finished : NotificationsSettingsState()
}