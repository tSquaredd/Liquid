package com.tsquaredapplications.liquid.settings.notifications

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentNotificationsSettingBinding
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisible
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

class NotificationsSettingFragment : BaseFragment<FragmentNotificationsSettingBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NotificationsSettingsViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsSettingBinding =
        FragmentNotificationsSettingBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateButton.setOnClickListener { viewModel.onUpdateClicked() }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start()
    }

    private fun onStateChanged(state: NotificationsSettingsState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is StartTimeUpdated -> onStartTimeChanged(state)
            is EndTimeUpdated -> onEndTimeChanged(state)
            is UpdateIntervalOptions -> onUpdateIntervalOptions(state)
            is NotificationsEnabled -> onNotificationsEnabled()
            is NotificationsDisabled -> onNotificationsDisabled()
            is UpdateButtonEnabled -> binding.updateButton.isEnabled = true
            is UpdateButtonDisabled -> binding.updateButton.isEnabled = false
            is Finished -> findNavController().popBackStack()
        }
    }

    private fun onInitialized(state: Initialized) {
        with(binding.startTimeEditText) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    TimePickerDialog(
                        context,
                        R.style.TimePickerStyle,
                        { _, hour, min ->
                            viewModel.onStartTimeChanged(hour, min)
                        }, state.startTime.hour, state.startTime.min, false
                    )
                        .show()
                }
            }
            showSoftInputOnFocus = false
            setText(state.startTimeString)
        }

        with(binding.endTimeEditText) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    TimePickerDialog(
                        context,
                        R.style.TimePickerStyle,
                        { _, hour, min ->
                            viewModel.onEndTimeChanged(hour, min)
                        }, state.endTime.hour, state.endTime.min, false
                    )
                        .show()
                }
            }
            showSoftInputOnFocus = false
            setText(state.endTimeString)
        }

        binding.reminderSwitch.isChecked = state.enabled
        binding.reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.allowRemindersChanged(isChecked)
        }

        setHourIntervals(state.hourIntervalOptions)
        setMinIntervals(state.minsIntervalOptions)

        if (state.enabled) {
            binding.mask.setAsGone()
        } else {
            binding.mask.setAsVisible()
        }
    }

    private fun onStartTimeChanged(state: StartTimeUpdated) {
        with(binding.startTimeEditText) {
            setText(state.timeString)
            clearFocus()
        }
    }

    private fun onEndTimeChanged(state: EndTimeUpdated) {
        with(binding.endTimeEditText) {
            setText(state.timeString)
            clearFocus()
        }
    }

    private fun onUpdateIntervalOptions(state: UpdateIntervalOptions) {
        setHourIntervals(state.hourIntervalOptions)
        setMinIntervals(state.minsIntervalOptions)
    }

    private fun setHourIntervals(hourIntervalOptions: Pair<Int, Int>) {
        with(binding.hourNumberPicker) {
            minValue = 1
            maxValue = hourIntervalOptions.first
            value = hourIntervalOptions.second
            setOnValueChangedListener { _, _, hours ->
                viewModel.selectedHourIntervalChanged(hours)
            }
        }
    }

    private fun setMinIntervals(minsIntervalOptions: Pair<Array<String>, Int>) {
        with(binding.minuteNumberPicker) {
            minValue = 0
            maxValue = 1
            displayedValues = minsIntervalOptions.first
            value = if (minsIntervalOptions.second == 0) 0 else 1
            setOnValueChangedListener { _, _, index ->
                viewModel.selectedMinuteIntervalChanged(displayedValues[index].toInt())
            }
        }
    }

    private fun onNotificationsEnabled() {
        binding.mask.setAsGone()
    }

    private fun onNotificationsDisabled() {
        binding.mask.setAsVisible()
    }
}