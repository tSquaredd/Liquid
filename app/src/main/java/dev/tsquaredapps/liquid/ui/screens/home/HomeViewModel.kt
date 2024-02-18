package dev.tsquaredapps.liquid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tsquaredapps.liquid.data.entry.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val entryRepository: EntryRepository
) : ViewModel() {

    val entryLiveData = entryRepository.getAllFlow().asLiveData()

    fun onAddClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            entryRepository.addEntry()
        }
    }
}