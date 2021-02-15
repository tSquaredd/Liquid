package com.tsquaredapplications.liquid.add.amount

import android.app.DatePickerDialog
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.add.amount.DrinkAmountFragmentDirections.Companion.toHomeFragment
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.DrinkAdded
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.Initialized
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.InvalidAmount
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.UpdateDateString
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.databinding.FragmentDrinkAmountBinding
import com.tsquaredapplications.liquid.ext.day
import com.tsquaredapplications.liquid.ext.keyboardHidingFocusChangeListener
import com.tsquaredapplications.liquid.ext.month
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.year
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrinkAmountFragment : BaseFragment<FragmentDrinkAmountBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DrinkAmountViewModel> { viewModelFactory }
    private val args by navArgs<DrinkAmountFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDrinkAmountBinding = FragmentDrinkAmountBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.shared_image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewCompat.setTransitionName(binding.drinkIcon, DRINK_ICON_TRANSITION_NAME)

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
            is UpdateDateString -> updateDateString(state)
            is DrinkAdded -> onDrinkAdded()
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.drinkTypeName.text = args.drinkType.drinkType.name
        binding.amountSelectionTextLayout.hint = state.unitPreference

        with(binding.dateSelectedEditText) {
            setText(state.dateString)
            showSoftInputOnFocus = false
            setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        R.style.DatePickerDialogStyle,
                        viewModel,
                        state.calendar.year,
                        state.calendar.month,
                        state.calendar.day
                    )

                    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

                    datePickerDialog.show()
                    view.clearFocus()
                }
            }
        }

        GlideApp.with(binding.drinkIcon.context)
            .load(args.drinkType.icon.largeIconResource)
            .placeholder(R.drawable.drink_type_placeholder_large)
            .into(binding.drinkIcon)
    }

    private fun onInvalidAmount(state: InvalidAmount) {
        binding.amountSelectionTextLayout.error = state.errorMessage
    }

    private fun updateDateString(state: UpdateDateString) {
        binding.dateSelectedEditText.setText(state.dateString)
    }

    private fun onDrinkAdded() {
        navigate(toHomeFragment(true))
    }

    companion object {
        const val DRINK_ICON_TRANSITION_NAME = "drink_image"
    }
}