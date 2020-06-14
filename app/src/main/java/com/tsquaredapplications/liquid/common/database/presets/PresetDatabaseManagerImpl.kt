package com.tsquaredapplications.liquid.common.database.presets

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tsquaredapplications.liquid.common.PRESETS
import com.tsquaredapplications.liquid.common.USERS
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import javax.inject.Inject

class PresetDatabaseManagerImpl
@Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : PresetDatabaseManager {

    override fun addPreset(
        name: String,
        sizeInOz: Double,
        drinkType: DrinkType,
        icon: Icon,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        val userId = auth.uid
        if (userId == null) {
            onFailure(null)
            return
        }

        val newPresetRef = db.collection(USERS).document(userId).collection(PRESETS).document()
        val preset = Preset(
            name = name,
            sizeInOz = sizeInOz,
            drinkType = drinkType,
            icon = icon,
            dbKey = newPresetRef.id
        )

        newPresetRef.set(preset)
            .addOnSuccessListener {
                onSuccess(preset)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    override fun getAllPresets(onSuccess: (List<Preset>) -> Unit, onFailure: (Exception?) -> Unit) {
        val userId = auth.uid
        if (userId == null) {
            onFailure(null)
            return
        }

        db.collection(USERS).document(userId).collection(PRESETS).get()
            .addOnSuccessListener { querySnapshot ->
                val presets = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject<Preset>()
                }
                onSuccess(presets)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    override fun delete(
        preset: Preset,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        val userId = auth.uid
        if (userId == null) {
            onFailure(null)
            return
        }

        db.collection(USERS).document(userId).collection(PRESETS).document(preset.dbKey).delete()
            .addOnSuccessListener { onSuccess(preset) }
            .addOnFailureListener { onFailure(it) }
    }

    override fun update(
        preset: Preset,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        val userId = auth.uid
        if (userId == null) {
            onFailure(null)
            return
        }

        val presetRef =
            db.collection(USERS).document(userId).collection(PRESETS).document(preset.dbKey)

        presetRef.set(preset)
            .addOnSuccessListener { onSuccess(preset) }
            .addOnFailureListener { onFailure(it) }
    }
}