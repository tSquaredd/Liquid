package com.tsquaredapplications.liquid.common.database.presets

import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import javax.inject.Inject

class RoomPresetRepository
@Inject constructor(
    private val presetDao: PresetDao,
    private val iconRepository: IconRepository,
    private val drinkTypeRepository: TypeRepository
) : PresetRepository {

    override suspend fun getAllPresets(): Map<Int, PresetDataWrapper> {
        val presets = presetDao.getAllPresets()
        val icons = iconRepository.getAllIcons()
        val drinkTypes = drinkTypeRepository.getAllDrinkTypes()

        return presets.map { preset ->
            preset.presetUid to PresetDataWrapper(
                preset,
                icons[preset.iconUid] ?: IconRepository.DEFAULT_ICON,
                drinkTypes[preset.drinkTypeUid]
                    ?: throw IllegalStateException("Preset must have drink type")
            )
        }.toMap()
    }

    override suspend fun insert(preset: Preset) {
        presetDao.insert(preset)
    }

    override suspend fun delete(preset: Preset) {
        presetDao.delete(preset)
    }

    override suspend fun update(preset: Preset) {
        presetDao.update(preset)
    }
}