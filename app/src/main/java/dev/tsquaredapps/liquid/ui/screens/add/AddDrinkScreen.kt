package dev.tsquaredapps.liquid.ui.screens.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.tsquaredapps.liquid.R
import dev.tsquaredapps.liquid.data.type.DrinkType
import dev.tsquaredapps.liquid.ui.theme.h20

@Composable
fun AddDrinkScreen(
    navigateToAmountSelection: (type: DrinkType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDrinkViewModel = hiltViewModel()
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PresetsSection(modifier = Modifier.padding(30.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(30.dp))
            DrinkTypesSection(onDrinkTypeSelected = viewModel::onDrinkTypeSelected)
        }
    }
}

@Composable
private fun PresetsSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.add_presets_prompt),
            fontSize = TextUnit(24f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DrinkTypesSection(
    onDrinkTypeSelected: (type: DrinkType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(125.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DrinkType.values().forEach { drinkType ->
            item {
                DrinkTypeItem(type = drinkType, onDrinkTypeSelected = onDrinkTypeSelected)
            }
        }
    }
}

@Composable
private fun DrinkTypeItem(
    type: DrinkType,
    onDrinkTypeSelected: (type: DrinkType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onDrinkTypeSelected(type) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(color = h20, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(60.dp),
                painter = painterResource(id = type.drawableRes),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = type.nameRes), fontSize = TextUnit(16f, TextUnitType.Sp))
    }
}