package com.tsquaredapplications.liquid.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.icons.IconDao
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDao
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeDao
import java.util.concurrent.Executors

@Database(
    entities = [DrinkType::class, Icon::class, Preset::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinkTypeDao(): DrinkTypeDao
    abstract fun iconDao(): IconDao
    abstract fun presetDao(): PresetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "LiquidDatabase.db"
        )
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        getInstance(context).drinkTypeDao()
                            .insertAll(prePopulateDrinkTypes(context))
                        getInstance(context).iconDao().insertAll(PRE_POPULATED_ICON_TYPES)
                    }
                }
            })
            .build()

        fun prePopulateDrinkTypes(context: Context) =
            listOf(
                DrinkType(1, context.getString(R.string.beer), -1.0, 1),
                DrinkType(2, context.getString(R.string.cocktail), -1.0, 2),
                DrinkType(3, context.getString(R.string.coffee), 1.0, 3),
                DrinkType(4, context.getString(R.string.energy_drink), 0.5, 4),
                DrinkType(5, context.getString(R.string.iced_tea), 1.0, 5),
                DrinkType(6, context.getString(R.string.juice), 1.0, 6),
                DrinkType(7, context.getString(R.string.milk), 1.0, 7),
                DrinkType(8, context.getString(R.string.smoothie), 1.0, 8),
                DrinkType(9, context.getString(R.string.soda), 1.0, 9),
                DrinkType(10, context.getString(R.string.sparkling_water), 1.0, 10),
                DrinkType(11, context.getString(R.string.spirit), -8.0, 11),
                DrinkType(12, context.getString(R.string.sports_drink), 1.0, 12),
                DrinkType(13, context.getString(R.string.tea), 1.0, 13),
                DrinkType(14, context.getString(R.string.water), 1.0, 14),
                DrinkType(15, context.getString(R.string.wine), -2.66, 15)
            )

        val PRE_POPULATED_ICON_TYPES = listOf(
            Icon(1, R.drawable.beer, R.drawable.beer_large),
            Icon(2, R.drawable.cocktail, R.drawable.cocktail_large),
            Icon(3, R.drawable.coffee, R.drawable.coffee_large),
            Icon(4, R.drawable.energy_drink, R.drawable.energy_drink_large),
            Icon(5, R.drawable.iced_tea, R.drawable.iced_tea_large),
            Icon(6, R.drawable.juice, R.drawable.juice_large),
            Icon(7, R.drawable.milk, R.drawable.milk_large),
            Icon(8, R.drawable.smoothie, R.drawable.smoothie_large),
            Icon(9, R.drawable.soda, R.drawable.soda_large),
            Icon(10, R.drawable.sparkling_water, R.drawable.sparkling_water_large),
            Icon(11, R.drawable.spirit, R.drawable.spirit_large),
            Icon(12, R.drawable.sports_drink, R.drawable.sports_drink_large),
            Icon(13, R.drawable.tea, R.drawable.tea_large),
            Icon(14, R.drawable.water, R.drawable.water_large),
            Icon(15, R.drawable.wine, R.drawable.wine_large),
            Icon(16, R.drawable.bottle, R.drawable.bottle_large),
            Icon(17, R.drawable.bourbon, R.drawable.bourbon_large),
            Icon(18, R.drawable.pink_tea_cup, R.drawable.pink_tea_cup_large),
            Icon(19, R.drawable.pink_tini, R.drawable.pink_tini_large),
            Icon(20, R.drawable.small_bottle, R.drawable.small_bottle_large),
            Icon(21, R.drawable.travel_mug, R.drawable.travel_mug_large)
        )
    }
}