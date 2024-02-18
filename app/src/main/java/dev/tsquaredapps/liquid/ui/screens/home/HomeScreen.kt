package dev.tsquaredapps.liquid.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.tsquaredapps.liquid.ui.theme.coral

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.entryLiveData.observeAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = coral,
                onClick = viewModel::onAddClicked) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            state?.let {
                items(it) {
                    Text(text = it.id.toString())
                }
            }
        }
    }
}