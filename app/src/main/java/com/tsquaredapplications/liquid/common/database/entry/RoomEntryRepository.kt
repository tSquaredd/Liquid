package com.tsquaredapplications.liquid.common.database.entry

import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import javax.inject.Inject

class RoomEntryRepository
@Inject constructor(
    private val entryDao: EntryDao,
    private val presetRepository: PresetRepository,
    private val drinkTypeRepository: TypeRepository
) : EntryRepository {
    override suspend fun getAll(): List<EntryDataWrapper> {
        return entriesToEntryDataWrappers(entryDao.getAllEntries())
    }

    override suspend fun getAllInTimeRange(from: Long, to: Long): List<EntryDataWrapper> {
        return entriesToEntryDataWrappers(entryDao.getAllForTimeRange(from, to))
    }

    private suspend fun entriesToEntryDataWrappers(entries: List<Entry>): List<EntryDataWrapper> {
        val presets = presetRepository.getAllPresets()
        val drinkTypes = drinkTypeRepository.getAllDrinkTypesWithIcons()

        val entryList = mutableListOf<EntryDataWrapper>()
        entries.forEach { entry ->
            val drinkTypeAndIcon = drinkTypes[entry.drinkTypeUid]
            val drinkType = drinkTypeAndIcon?.drinkType

            val presetAndIcon = presets[entry.presetUid]
            val preset = presetAndIcon?.preset

            val icon = presetAndIcon?.icon ?: drinkTypeAndIcon?.icon ?: IconRepository.DEFAULT_ICON

            if (drinkType != null) {
                entryList.add(
                    EntryDataWrapper(
                        entry, drinkType, preset, icon
                    )
                )
            }
        }

        return entryList
    }

    override suspend fun insert(entry: Entry) {
        entryDao.insert(entry)
    }

    override suspend fun insertAll(entries: List<Entry>) {
        entryDao.insertAll(entries)
    }

    override suspend fun update(entry: Entry) {
        entryDao.update(entry)
    }

    override suspend fun delete(entry: Entry) {
        entryDao.delete(entry)
    }
}