package com.tsquaredapplications.liquid.settings.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentSettingsBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.settings.main.SettingType.DailyGoal
import com.tsquaredapplications.liquid.settings.main.SettingType.Notifications
import com.tsquaredapplications.liquid.settings.main.SettingType.RateThisApp
import com.tsquaredapplications.liquid.settings.main.SettingType.UnitPreference
import com.tsquaredapplications.liquid.settings.main.SettingType.Weight
import com.tsquaredapplications.liquid.settings.main.SettingsFragmentDirections.Companion.toDailyGoalSettingFragment
import com.tsquaredapplications.liquid.settings.main.SettingsFragmentDirections.Companion.toLiquidUnitSettingFragment
import com.tsquaredapplications.liquid.settings.main.SettingsFragmentDirections.Companion.toNotificationSettingFragment
import com.tsquaredapplications.liquid.settings.main.SettingsFragmentDirections.Companion.toWeightSettingFragment
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SettingsViewModel> { viewModelFactory }

    private val itemAdapter = ItemAdapter<SettingsItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            when (item.setting.settingType) {
                DailyGoal -> navigate(toDailyGoalSettingFragment())
                Weight -> navigate(toWeightSettingFragment())
                UnitPreference -> navigate(toLiquidUnitSettingFragment())
                Notifications -> navigate(toNotificationSettingFragment())
                RateThisApp -> {
                }
            }
            false
        }

        with(itemAdapter) {
            clear()
            add(viewModel.getSettingsItems())
        }
    }
}