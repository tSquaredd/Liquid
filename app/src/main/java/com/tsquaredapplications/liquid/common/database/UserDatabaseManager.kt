package com.tsquaredapplications.liquid.common.database

import com.tsquaredapplications.liquid.common.UserInformation

interface UserDatabaseManager {
    fun setUser(
        userInformation: UserInformation,
        onCompletion: () -> Unit,
        onFail: () -> Unit,
        onSuccess: () -> Unit
    )
}