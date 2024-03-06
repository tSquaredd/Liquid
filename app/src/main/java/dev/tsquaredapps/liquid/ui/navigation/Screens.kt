package dev.tsquaredapps.liquid.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import dev.tsquaredapps.liquid.data.type.DrinkType
import dev.tsquaredapps.liquid.ui.screens.add.AddDrinkScreen
import dev.tsquaredapps.liquid.ui.screens.amount.SelectAmountScreen
import dev.tsquaredapps.liquid.ui.screens.history.HistoryScreen
import dev.tsquaredapps.liquid.ui.screens.home.HomeScreen
import dev.tsquaredapps.liquid.ui.screens.presets.PresetsScreen
import dev.tsquaredapps.liquid.ui.screens.settings.SettingsScreen
import dev.tsquaredapps.liquid.ui.screens.stats.StatsScreen
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Screen {
    val route: String
}

interface NoArgScreen : Screen {
    fun destination(builder: NavGraphBuilder, navController: NavController)
}

interface ArgScreen<T> : Screen {
    val baseRoute: String
    val navType: NavType<T>

    override val route: String
        get() = "$baseRoute/{$argKey}"

    val argKey: String
        get() = "${baseRoute}_arg_key"

    fun createRoute(args: T) = "$baseRoute/${formatArgs(args)}"
    fun formatArgs(args: T): String
    fun destination(builder: NavGraphBuilder, navController: NavController, defaultArg: T? = null)
}

object Screens {
    object Home : NoArgScreen {
        override val route: String = "home"

        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                HomeScreen(navigateToAddDrink = {
                    navController.navigate(AddDrink.route)
                })
            }
    }

    object History : NoArgScreen {
        override val route: String = "history"

        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                HistoryScreen()
            }
    }

    object Presets : NoArgScreen {
        override val route: String = "presets"

        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                PresetsScreen()
            }
    }

    object Stats : NoArgScreen {
        override val route: String = "stats"

        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                StatsScreen()
            }
    }

    object Settings : NoArgScreen {
        override val route: String = "settings"

        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                SettingsScreen()
            }
    }

    object AddDrink : NoArgScreen {
        override val route: String = "add_drink"
        override fun destination(builder: NavGraphBuilder, navController: NavController) =
            builder.composable(route) {
                AddDrinkScreen(navigateToAmountSelection = {
                    navController.navigate(AmountSelection.createRoute(AmountSelection.Args(it)))
                })
            }
    }

    object AmountSelection : ArgScreen<AmountSelection.Args> {
        @Serializable
        @Parcelize
        data class Args(
            val drinkType: DrinkType
        ) : Parcelable

        override val baseRoute: String = "amount_selection"
        override val navType: NavType<Args> = generateNavType(isNullableAllowed = false)

        override fun destination(
            builder: NavGraphBuilder,
            navController: NavController,
            defaultArg: Args?
        ) = builder.composable(route) {
            SelectAmountScreen()
        }

        override fun formatArgs(args: Args): String = Uri.encode(Json.encodeToString(args))
    }
}


inline fun <reified T : Parcelable> generateNavType(isNullableAllowed: Boolean) =
    object : NavType<T>(
        isNullableAllowed = isNullableAllowed
    ) {
        override fun get(bundle: Bundle, key: String): T {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, T::class.java)!!
            } else {
                bundle.getParcelable(key)!!
            }
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }
    }
