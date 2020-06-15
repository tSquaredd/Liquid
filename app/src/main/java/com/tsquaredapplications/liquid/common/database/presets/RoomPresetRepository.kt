package com.tsquaredapplications.liquid.common.database.presets

import javax.inject.Inject

class RoomPresetRepository
@Inject constructor(private val presetDao: PresetDao) : PresetRepository {

    override suspend fun getAllPresets(): List<PresetDataWrapper> = presetDao.getAllPresets()

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