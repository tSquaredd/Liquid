package com.tsquaredapplications.liquid.add.drink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.add.amount.DrinkAmountFragment
import com.tsquaredapplications.liquid.add.drink.SelectDrinkFragmentDirections.Companion.toDrinkAmountFragment
import com.tsquaredapplications.liquid.add.drink.SelectDrinkFragmentDirections.Companion.toHomeFragment
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.DrinkTypeSelected
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.PresetInserted
import com.tsquaredapplications.liquid.common.AlcoholWarningDialog
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.databinding.FragmentSelectDrinkBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectDrinkFragment : BaseFragment<FragmentSelectDrinkBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SelectDrinkViewModel> { viewModelFactory }
    private val presetItemAdapter = ItemAdapter<PresetItem>()
    private val presetsAdapter = FastAdapter.with(presetItemAdapter)
    private val typeItemAdapter = ItemAdapter<TypeItem>()
    private val drinkTypeAdapter = FastAdapter.with(typeItemAdapter)
    private var selectedTypeView: View? = null

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectDrinkBinding =
        FragmentSelectDrinkBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })
        viewModel.start()
    }

    private fun setupRecyclerViews() {
        with(binding.presetsRecyclerView) {
            adapter = presetsAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        presetsAdapter.onClickListener = { _, _, item, _ ->
            viewModel.presetSelected(item.presetDataWrapper)
            false
        }

        with(binding.drinkTypeRecyclerView) {
            adapter = drinkTypeAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        drinkTypeAdapter.onClickListener = { view, _, item, _ ->
            view?.let {
                selectedTypeView = it
                ViewCompat.setTransitionName(it, DrinkAmountFragment.DRINK_ICON_TRANSITION_NAME)
            }
            viewModel.onDrinkTypeSelected(item.drinkTypeAndIcon)
            false
        }
    }

    private fun onStateChanged(state: SelectDrinkState) {
        when (state) {
            is Initialized -> onInitialized(state)
            is PresetInserted -> onPresetInserted(state)
            is DrinkTypeSelected -> onDrinkTypeSelected(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        if (state.presets.isNotEmpty()) {
            binding.noPresetsHeader.setAsGone()
            binding.presetsRecyclerView.setAsVisible()
            presetItemAdapter.clear()
            presetItemAdapter.add(state.presets)
        } else {
            binding.noPresetsHeader.setAsVisible()
        }

        typeItemAdapter.clear()
        typeItemAdapter.add(state.drinkTypes)
    }

    private fun onPresetInserted(state: PresetInserted) {
        if (state.showAlcoholWarning) {
            AlcoholWarningDialog(
                calculationsText = state.alcoholCalculations,
                suggestionText = state.alcoholSuggestion,
                onDismiss = {
                    viewModel.alcoholWarningDismissed(it)
                    findNavController().popBackStack()
                }
            ).show(parentFragmentManager, null)
        } else {
            navigate(toHomeFragment(true))
        }
    }

    private fun onDrinkTypeSelected(state: DrinkTypeSelected) {
        val transitionSharedExtras = selectedTypeView?.let {
            FragmentNavigatorExtras(it to DrinkAmountFragment.DRINK_ICON_TRANSITION_NAME)
        } ?: kotlin.run { null }

        if (state.showAlcoholWarning) {
            AlcoholWarningDialog(
                calculationsText = state.alcoholCalculations,
                suggestionText = state.alcoholSuggestion,
                onDismiss = {
                    viewModel.alcoholWarningDismissed(it)
                    navigate(toDrinkAmountFragment(state.drinkTypeAndIcon))
                }
            ).show(parentFragmentManager, null)
        } else {
            transitionSharedExtras?.let {
                navigate(toDrinkAmountFragment(state.drinkTypeAndIcon), it)
            } ?: run {
                navigate(toDrinkAmountFragment(state.drinkTypeAndIcon))
            }
        }
    }
}