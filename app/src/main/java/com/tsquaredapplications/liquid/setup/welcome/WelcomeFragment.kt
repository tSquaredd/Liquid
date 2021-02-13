package com.tsquaredapplications.liquid.setup.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentWelcomeBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.setup.welcome.WelcomeFragmentDirections.Companion.toUserInformationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding =
        FragmentWelcomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getStartedButton.setOnClickListener {
            navigate(toUserInformationFragment())
        }

        TextViewCompat.setAutoSizeTextTypeWithDefaults(
            binding.welcomeText,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
        )
    }
}