package com.tsquaredapplications.liquid.add.drink

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.add.drink.SelectDrinkFragmentDirections.Companion.toDrinkAmountFragment
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.databinding.FragmentSelectDrinkBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisible
import javax.inject.Inject

class SelectDrinkFragment : BaseFragment<FragmentSelectDrinkBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SelectDrinkViewModel> { viewModelFactory }
    private val presetItemAdapter = ItemAdapter<PresetItem>()
    private val presetsAdapter = FastAdapter.with(presetItemAdapter)
    private val typeItemAdapter = ItemAdapter<TypeItem>()
    private val drinkTypeAdapter = FastAdapter.with(typeItemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectDrinkBinding =
        FragmentSelectDrinkBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

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
            // TODO LIQ-138
            false
        }

        with(binding.drinkTypeRecyclerView) {
            adapter = drinkTypeAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        drinkTypeAdapter.onClickListener = { _, _, item, _ ->
            navigate(toDrinkAmountFragment(item.drinkTypeAndIcon))
            false
        }
    }

    private fun onStateChanged(state: SelectDrinkState) {
        when (state) {
            is Initialized -> {
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
        }
    }
}