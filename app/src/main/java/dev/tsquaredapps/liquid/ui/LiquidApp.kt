package dev.tsquaredapps.liquid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavHost
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationActions
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationBar
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationRail
import dev.tsquaredapps.liquid.ui.navigation.LiquidRoute
import dev.tsquaredapps.liquid.ui.utils.LiquidNavigationType

@Composable
fun LiquidApp(
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        LiquidNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: LiquidRoute.HOME

    val navigationType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> LiquidNavigationType.BOTTOM_NAVIGATION
        else -> LiquidNavigationType.NAVIGATION_RAIL
    }

    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == LiquidNavigationType.NAVIGATION_RAIL) {
            LiquidNavigationRail(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = { destination ->
                    navigationActions.navigateTo(destination)
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            LiquidNavHost(
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(visible = navigationType == LiquidNavigationType.BOTTOM_NAVIGATION) {
                LiquidNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = { destination ->
                        navigationActions.navigateTo(destination)
                    }
                )
            }
        }
    }
}
