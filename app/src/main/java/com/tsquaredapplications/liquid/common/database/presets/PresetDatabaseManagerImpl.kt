package com.tsquaredapplications.liquid.common.database.presets

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tsquaredapplications.liquid.common.PRESETS
import com.tsquaredapplications.liquid.common.USERS
import javax.inject.Inject

class PresetDatabaseManagerImpl
@Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : PresetDatabaseManager {
    override fun addPreset(preset: Preset, onSuccess: () -> Unit, onFailure: (Exception?) -> Unit) {
        val userId = auth.uid
        if (userId == null) {
            onFailure(null)
            return
        }

        db.collection(USERS).document(userId).collection(PRESETS).add(preset)
            .addOnSuccessListener {
                onSuccess()
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
}