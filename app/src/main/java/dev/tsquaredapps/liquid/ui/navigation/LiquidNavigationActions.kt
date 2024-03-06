package dev.tsquaredapps.liquid.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.tsquaredapps.liquid.R

sealed class LiquidTopLevelDestination(
    val route: String,
    val selectedIconRes: Int,
    val unselectedIconRes: Int,
    val iconTextId: Int
) {
    data object Home: LiquidTopLevelDestination(
        route = "home_nav_route",
        selectedIconRes = R.drawable.ic_home_filled,
        unselectedIconRes = R.drawable.ic_home,
        iconTextId = R.string.home
    )

    data object History: LiquidTopLevelDestination(
        route = "history_nav_route",
        selectedIconRes = R.drawable.ic_history_filled,
        unselectedIconRes = R.drawable.ic_history,
        iconTextId = R.string.history
    )

    data object Presets: LiquidTopLevelDestination(
        route = "presets_nav_route",
        selectedIconRes = R.drawable.ic_presets_filled,
        unselectedIconRes = R.drawable.ic_presets,
        iconTextId = R.string.presets
    )

    data object Stats: LiquidTopLevelDestination(
        route = "stats_nav_route",
        selectedIconRes = R.drawable.ic_statistics_filled,
        unselectedIconRes = R.drawable.ic_statistics,
        iconTextId = R.string.stats
    )

    data object Settings: LiquidTopLevelDestination(
        route = "settings_nav_route",
        selectedIconRes = R.drawable.ic_settings_filled,
        unselectedIconRes = R.drawable.ic_settings,
        iconTextId = R.string.settings
    )
}

val TOP_LEVEL_DESTINATIONS = listOf(
    LiquidTopLevelDestination.Home,
    LiquidTopLevelDestination.History,
    LiquidTopLevelDestination.Presets,
    LiquidTopLevelDestination.Stats,
    LiquidTopLevelDestination.Settings

)

class LiquidNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: LiquidTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}