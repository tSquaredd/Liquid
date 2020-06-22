package com.tsquaredapplications.liquid.add.amount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.add.amount.DrinkAmountFragmentDirections.Companion.toHomeFragment
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.DrinkAdded
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.Initialized
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.InvalidAmount
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.databinding.FragmentDrinkAmountBinding
import com.tsquaredapplications.liquid.ext.keyboardHidingFocusChangeListener
import com.tsquaredapplications.liquid.ext.navigate
import java.util.*
import javax.inject.Inject

class DrinkAmountFragment : BaseFragment<FragmentDrinkAmountBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DrinkAmountViewModel> { viewModelFactory }
    private val args by navArgs<DrinkAmountFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDrinkAmountBinding = FragmentDrinkAmountBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addButton.setOnClickListener {
            viewModel.onAddClicked()
        }

        with(binding.amountSelectionEditText) {
            addTextChangedListener {
                it?.let {
                    viewModel.onAmountChanged(it.toString())
                    binding.amountSelectionTextLayout.error = ""
                }
            }

            onFocusChangeListener = keyboardHidingFocusChangeListener
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start(args.drinkType.drinkType)
    }

    private fun onStateChanged(state: DrinkAmountState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is InvalidAmount -> onInvalidAmount(state)
            is DrinkAdded -> onDrinkAdded()
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.drinkTypeName.text = args.drinkType.drinkType.name
        binding.amountSelectionTextLayout.hint = state.unitPreference

        GlideApp.with(binding.presetIcon.context)
            .load(args.drinkType.icon.largeIconResource)
            .placeholder(R.drawable.drink_type_placeholder_large)
            .into(binding.presetIcon)

        binding.datePicker.init(
            state.today.get(Calendar.YEAR),
            state.today.get(Calendar.MONTH),
            state.today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dayOfMonth ->
            viewModel.onDateChanged(
                year,
                monthOfYear,
                dayOfMonth
            )
        }
        binding.datePicker.maxDate = System.currentTimeMillis() - 1000
    }

    private fun onInvalidAmount(state: InvalidAmount) {
        binding.amountSelectionTextLayout.error = state.errorMessage
    }

    private fun onDrinkAdded() {
        navigate(toHomeFragment())
    }
}