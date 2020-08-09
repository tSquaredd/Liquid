package com.tsquaredapplications.liquid.common.database.users

interface UserManager {
    fun setUser(userInformation: UserInformation)
    fun getUser(): UserInformation?
    fun shouldShowAlcoholWarning(): Boolean
    fun setDonNotShowAlcoholWarning()
    fun updateGoal(goal: Int)
}