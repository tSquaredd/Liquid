package com.tsquaredapplications.liquid.settings.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentNotificationsSettingBinding

class NotificationsSettingFragment : BaseFragment<FragmentNotificationsSettingBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsSettingBinding =
        FragmentNotificationsSettingBinding.inflate(inflater, container, false)
}