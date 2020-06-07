package com.tsquaredapplications.liquid.common.database.icons

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tsquaredapplications.liquid.common.PRESET_ICONS
import javax.inject.Inject

class PresetIconDatabaseManagerImpl
@Inject constructor(private val db: FirebaseFirestore) : PresetIconDatabaseManager {
    override fun getPresetIcons(
        onSuccess: (List<Icon>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection(PRESET_ICONS).get()
            .addOnSuccessListener { querySnapshot ->
                val presetIcons = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot?.toObject<Icon>()
                }
                onSuccess(presetIcons)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}