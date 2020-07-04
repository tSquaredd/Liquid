package com.tsquaredapplications.liquid.common.database.types

import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import javax.inject.Inject

class RoomTypeRepository
@Inject constructor(
    private val drinkTypeDao: DrinkTypeDao,
    private val iconRepository: IconRepository
) : TypeRepository {
    override suspend fun getAllDrinkTypesWithIcons(): Map<Int, DrinkTypeAndIcon> {
        val drinkTypes = drinkTypeDao.getAll()
        val icons = iconRepository.getAllIcons()

        return drinkTypes.map { drinkType ->
            drinkType.drinkTypeUid to DrinkTypeAndIcon(
                drinkType,
                icons[drinkType.iconUid] ?: IconRepository.DEFAULT_ICON
            )
        }.toMap()
    }

    override suspend fun getAllDrinkTypes(): Map<Int, DrinkType> =
        drinkTypeDao.getAll().map { it.drinkTypeUid to it }.toMap()
}
