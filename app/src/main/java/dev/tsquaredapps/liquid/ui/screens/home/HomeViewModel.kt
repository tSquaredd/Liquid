package dev.tsquaredapps.liquid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tsquaredapps.liquid.data.entry.EntryRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val entryRepository: EntryRepository
) : ViewModel() {

    val entryLiveData = entryRepository.getAllFlow().asLiveData()

}