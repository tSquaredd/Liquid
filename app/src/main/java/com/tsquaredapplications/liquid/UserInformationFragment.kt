package com.tsquaredapplications.liquid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentUserInformationBinding
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.UnitChoiceState
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UserInformationFragment : BaseFragment<FragmentUserInformationBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UserInformationViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserInformationBinding =
        FragmentUserInformationBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUnitChoiceLiveData().observe(viewLifecycleOwner, Observer {
            onUnitChoiceStateChanged(it)
        })

        binding.ozButton.setOnClickListener {
            viewModel.onUnitSelected(UnitChoiceState.OZ)
        }

        binding.mlButton.setOnClickListener {
            viewModel.onUnitSelected(UnitChoiceState.ML)
        }

        viewModel.start()
    }

    private fun onUnitChoiceStateChanged(state: UnitChoiceState) {
        when (state) {
            UnitChoiceState.OZ -> setOzChecked()
            UnitChoiceState.ML -> setMlChecked()
        }
    }

    private fun setOzChecked() {
        binding.ozButton.isChecked = true
        binding.mlButton.isChecked = false
    }

    private fun setMlChecked() {
        binding.mlButton.isChecked = true
        binding.ozButton.isChecked = false
    }

}
