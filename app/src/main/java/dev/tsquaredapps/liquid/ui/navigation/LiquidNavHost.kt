package dev.tsquaredapps.liquid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.tsquaredapps.liquid.ui.screens.add.AddDrinkScreen
import dev.tsquaredapps.liquid.ui.screens.add.AddDrinkViewModel
import dev.tsquaredapps.liquid.ui.screens.amount.SelectAmountScreen
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
        startDestination = LiquidTopLevelDestination.Home.route,
    ) {
        navigation(startDestination = Screens.Home.route, route = LiquidTopLevelDestination.Home.route) {
            Screens.Home.destination(this, navController)
        }
        navigation(startDestination = Screens.History.route, route = LiquidTopLevelDestination.History.route) {
            Screens.History.destination(this, navController)
        }
        navigation(startDestination = Screens.Presets.route, route = LiquidTopLevelDestination.Presets.route) {
            Screens.Presets.destination(this, navController)
        }
        navigation(startDestination = Screens.Stats.route, route = LiquidTopLevelDestination.Stats.route) {
            Screens.Stats.destination(this, navController)
        }
        navigation(startDestination = Screens.Settings.route, route = LiquidTopLevelDestination.Settings.route) {
            Screens.Settings.destination(this, navController)
        }

        Screens.AddDrink.destination(builder = this, navController = navController)
        Screens.AmountSelection.destination(builder = this, navController = navController)
    }
}