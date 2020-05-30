package com.tsquaredapplications.liquid.common.database.types

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Type(
    val name: String = "",
    val hydration: Int = 1,
    val iconUrl: String = ""
)