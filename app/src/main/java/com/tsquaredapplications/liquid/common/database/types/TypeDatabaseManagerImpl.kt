package com.tsquaredapplications.liquid.common.database.types

import com.google.firebase.firestore.FirebaseFirestore
import com.tsquaredapplications.liquid.common.TYPES
import com.tsquaredapplications.liquid.common.database.icons.Icon
import javax.inject.Inject

class TypeDatabaseManagerImpl
@Inject constructor(
    private val db: FirebaseFirestore
) : TypeDatabaseManager {
    override fun getTypes(onSuccess: (List<Type>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(TYPES).get()
            .addOnSuccessListener { querySnapshot ->
                val types = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    Type(
                        name = documentSnapshot.getString(NAME_KEY) ?: "",
                        hydration = documentSnapshot.getDouble(HYDRATION_KEY) ?: 1.0,
                        icon = Icon(
                            iconPath = documentSnapshot.getString(ICON_PATH_KEY) ?: "",
                            largeIconPath = documentSnapshot.getString(LARGE_ICON_PATH_KEY) ?: ""
                        )
                    )
                }
                onSuccess(types)
            }
            .addOnFailureListener { onFailure(it) }
    }

    companion object {
        const val NAME_KEY = "name"
        const val HYDRATION_KEY = "hydration"
        const val ICON_PATH_KEY = "iconPath"
        const val LARGE_ICON_PATH_KEY = "largeIconPath"

    }
}