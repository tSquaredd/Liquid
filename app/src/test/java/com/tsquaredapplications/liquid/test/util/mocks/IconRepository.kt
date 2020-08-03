package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockIconRepository() = mockk<IconRepository> {
    val icons = mapOf(
        MOCK_WATER_ICON_UID to mockWaterIcon(),
        MOCK_BEER_ICON_UID to mockBeerIcon(),
        MOCK_TEA_ICON_UID to mockTeaIcon()
    )

    coEvery { getAllIcons() } returns icons
}