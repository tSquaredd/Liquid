package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.users.UserManager
import io.mockk.every
import io.mockk.mockk

fun mockUserManager(): UserManager = mockk(relaxUnitFun = true) {
    every { getUser() } returns mockUserInformation()
}