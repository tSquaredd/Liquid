package com.tsquaredapplications.liquid.common.database.users

interface UserDatabaseManager {
    fun setUser(
        userInformation: UserInformation,
        onCompletion: () -> Unit,
        onFail: () -> Unit,
        onSuccess: (UserInformation) -> Unit
    )

    fun getUser(
        onFail: () -> Unit,
        onSuccess: (UserInformation) -> Unit
    )
}