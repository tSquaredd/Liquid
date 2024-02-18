package dev.tsquaredapps.liquid.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.tsquaredapps.liquid.R

object LiquidRoute {
    const val HOME = "Home"
    const val HISTORY = "History"
    const val PRESETS = "Presets"
    const val STATS = "Stats"
    const val SETTINGS = "Settings"
}

data class LiquidTopLevelDestination(
    val route: String,
    val selectedIconRes: Int,
    val unselectedIconRes: Int,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    LiquidTopLevelDestination(
        route = LiquidRoute.HOME,
        selectedIconRes = R.drawable.ic_home_filled,
        unselectedIconRes = R.drawable.ic_home,
        iconTextId = R.string.home
    ),
    LiquidTopLevelDestination(
        route = LiquidRoute.HISTORY,
        selectedIconRes = R.drawable.ic_history_filled,
        unselectedIconRes = R.drawable.ic_history,
        iconTextId = R.string.history
    ),
    LiquidTopLevelDestination(
        route = LiquidRoute.PRESETS,
        selectedIconRes = R.drawable.ic_presets_filled,
        unselectedIconRes = R.drawable.ic_presets,
        iconTextId = R.string.presets
    ),
    LiquidTopLevelDestination(
        route = LiquidRoute.STATS,
        selectedIconRes = R.drawable.ic_statistics_filled,
        unselectedIconRes = R.drawable.ic_statistics,
        iconTextId = R.string.stats
    ),
    LiquidTopLevelDestination(
        route = LiquidRoute.SETTINGS,
        selectedIconRes = R.drawable.ic_settings_filled,
        unselectedIconRes = R.drawable.ic_settings,
        iconTextId = R.string.settings
    )

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