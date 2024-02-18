package dev.tsquaredapps.liquid.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.tsquaredapps.liquid.R
import dev.tsquaredapps.liquid.ui.theme.coral

@Composable
fun LiquidNavigationRail(
    selectedDestination: String,
    navigateToTopLevelDestination: (LiquidTopLevelDestination) -> Unit
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected by remember {
                    mutableStateOf(selectedDestination == destination.route)
                }
                NavigationRailItem(
                    selected = selected,
                    onClick = { navigateToTopLevelDestination(destination) },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (selected) {
                                    destination.selectedIconRes
                                } else {
                                    destination.unselectedIconRes
                                }
                            ),
                            contentDescription = stringResource(id = destination.iconTextId)
                        )
                    }
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                containerColor = coral
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun LiquidNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (LiquidTopLevelDestination) -> Unit
) {
    NavigationBar {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected by remember(selectedDestination) {
                mutableStateOf(destination.route == selectedDestination)
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navigateToTopLevelDestination(destination)
                },
                icon = {
                    Image(
                        painter = painterResource(
                            id = if (selected) {
                                destination.selectedIconRes
                            } else {
                                destination.unselectedIconRes
                            }
                        ),
                        contentDescription = stringResource(id = destination.iconTextId)
                    )
                }
            )
        }
    }
}
