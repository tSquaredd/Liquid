package dev.tsquaredapps.liquid.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavHost
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationActions
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationBar
import dev.tsquaredapps.liquid.ui.navigation.LiquidNavigationRail
import dev.tsquaredapps.liquid.ui.navigation.LiquidTopLevelDestination
import dev.tsquaredapps.liquid.ui.navigation.Screens
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

    val navigationType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> LiquidNavigationType.BOTTOM_NAVIGATION
        else -> LiquidNavigationType.NAVIGATION_RAIL
    }

    val currentDestination = navController.currentBackStackEntryAsState()

    @SuppressLint("RestrictedApi")
    val currentBackStack = navController.currentBackStack.collectAsState()
    val topLevelDestinationRoute =
        currentDestination.value?.destination?.parent?.route ?: currentBackStack.value.reversed()
            // this is needed to find current parent of global destinations
            .firstOrNull { it.destination is NavGraph }?.destination?.route ?: Screens.Home.route

    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == LiquidNavigationType.NAVIGATION_RAIL) {
            LiquidNavigationRail(
                topLevelDestinationRoute = topLevelDestinationRoute,
                onDestinationSelected = { destination ->
                    navigationActions.navigateTo(destination)
                },
                onAddDrinkClicked = {
                    navController.navigate(Screens.AddDrink.route)
                }
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
                    topLevelDestinationRoute = topLevelDestinationRoute,
                    onDestinationSelected = { destination ->
                        if (topLevelDestinationRoute == destination.route) {
                            navController.popBackStack(
                                route = destination.route,
                                inclusive = false
                            )
                        } else {
                            navController.navigate(
                                route = destination.route
                            ) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}
