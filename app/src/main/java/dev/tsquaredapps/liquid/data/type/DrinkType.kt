package dev.tsquaredapps.liquid.data.type

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.tsquaredapps.liquid.R
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed class DrinkType(
    val id: Int,
    val hydrationValue: Float,
    @StringRes val nameRes: Int,
    @DrawableRes val drawableRes: Int
): Parcelable {
    data object Water : DrinkType(
        id = 0,
        hydrationValue = 1f,
        nameRes = R.string.drink_type_water,
        drawableRes = R.drawable.water
    )

    data object Milk : DrinkType(
        id = 1,
        hydrationValue = 1.2f,
        nameRes = R.string.drink_type_milk,
        drawableRes = R.drawable.milk
    )

    data object Juice : DrinkType(
        id = 2,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_juice,
        drawableRes = R.drawable.juice
    )

    data object Soda : DrinkType(
        id = 3,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_soda,
        drawableRes = R.drawable.coke
    )

    data object HotTea : DrinkType(
        id = 4,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_hot_tea,
        drawableRes = R.drawable.hot_tea
    )

    data object Coffee : DrinkType(
        id = 5,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_coffee,
        drawableRes = R.drawable.coffee
    )

    data object Energy : DrinkType(
        id = 6,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_energy,
        drawableRes = R.drawable.energy_drink
    )

    data object Smoothie : DrinkType(
        id = 7,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_smoothie,
        drawableRes = R.drawable.smoothie
    )

    data object Sports : DrinkType(
        id = 8,
        hydrationValue = 1.0f,
        nameRes = R.string.drink_type_sports,
        drawableRes = R.drawable.sports_drink
    )

    data object Wine : DrinkType(
        id = 9,
        hydrationValue = -1.0f,
        nameRes = R.string.drink_type_wine,
        drawableRes = R.drawable.red_wine
    )

    data object Spirit : DrinkType(
        id = 10,
        hydrationValue = -1.0f,
        nameRes = R.string.drink_type_spirit,
        drawableRes = R.drawable.cosmo
    )

    data object Beer : DrinkType(
        id = 11,
        hydrationValue = -1.0f,
        nameRes = R.string.drink_type_beer,
        drawableRes = R.drawable.beer
    )

    companion object {
        fun values(): List<DrinkType> = listOf(
            Water,
            Milk,
            Juice,
            Soda,
            HotTea,
            Coffee,
            Energy,
            Smoothie,
            Sports,
            Wine,
            Spirit,
            Beer
        )
    }
}

