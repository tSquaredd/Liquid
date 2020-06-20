package com.tsquaredapplications.liquid.common.database.entry

import androidx.room.Embedded
import androidx.room.Relation
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType

class EntryDataWrapper(
    @Embedded val entry: Entry,
    @Relation(
        parentColumn = "drinkTypeUid",
        entityColumn = "drinkTypeUid",
        entity = DrinkType::class
    )
    val drinkType: DrinkType,
    @Relation(parentColumn = "presetUid", entityColumn = "presetUid", entity = Preset::class)
    val preset: Preset?
) {
}