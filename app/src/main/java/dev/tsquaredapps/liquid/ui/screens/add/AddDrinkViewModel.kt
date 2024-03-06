package dev.tsquaredapps.liquid.ui.screens.add

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tsquaredapps.liquid.data.type.DrinkType
import dev.tsquaredapps.liquid.util.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AddDrinkViewModel @Inject constructor(): ViewModel() {

    val navigationEvent = SingleLiveEvent<NavigationEvent>()

    sealed class NavigationEvent {
        data class ToAmountSelection(val drinkType: DrinkType): NavigationEvent()
    }

    fun onDrinkTypeSelected(drinkType: DrinkType) {
        navigationEvent.value = NavigationEvent.ToAmountSelection(drinkType)
    }
}