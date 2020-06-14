package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeDao
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem
import javax.inject.Inject

class PresetTypeSelectionViewModel
@Inject constructor(private val drinkTypeDao: DrinkTypeDao) : ViewModel() {

    private val state = SingleEventLiveData<PresetTypeSelectionState>()
    val stateLiveData: LiveData<PresetTypeSelectionState>
        get() = state

    fun getDrinkTypes(): LiveData<List<TypeItem>> = Transformations.map(drinkTypeDao.getAll()) {
        it.map { drinkTypeAndIcon ->
            TypeItem(drinkTypeAndIcon)
        }
    }

    fun onItemClick(item: TypeItem) {
        state.value = TypeSelected(item.drinkTypeAndIcon.drinkType)
    }
}