package dev.tsquaredapps.liquid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.tsquaredapps.liquid.ui.screens.add.AddDrinkScreen
import dev.tsquaredapps.liquid.ui.screens.history.HistoryScreen
import dev.tsquaredapps.liquid.ui.screens.home.HomeScreen
import dev.tsquaredapps.liquid.ui.screens.presets.PresetsScreen
import dev.tsquaredapps.liquid.ui.screens.settings.SettingsScreen
import dev.tsquaredapps.liquid.ui.screens.stats.StatsScreen

@Composable
fun LiquidNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LiquidRoute.HOME,
    ) {
        composable(LiquidRoute.HOME) {
            HomeScreen(navigateToAddDrink = {
                navController.navigate(LiquidRoute.ADD_DRINK)
            })
        }
        composable(LiquidRoute.HISTORY) {
            HistoryScreen()
        }
        composable(LiquidRoute.PRESETS) {
            PresetsScreen()
        }
        composable(LiquidRoute.STATS) {
            StatsScreen()
        }
        composable(LiquidRoute.SETTINGS) {
            SettingsScreen()
        }
        composable(LiquidRoute.ADD_DRINK) {
            AddDrinkScreen()
        }
    }
}