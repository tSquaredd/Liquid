package com.tsquaredapplications.liquid.common.database.types

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.tsquaredapplications.liquid.common.TYPES
import javax.inject.Inject

class TypeDatabaseManagerImpl
@Inject constructor(private val db: FirebaseFirestore) : TypeDatabaseManager {
    override fun getTypes(onSuccess: (List<Type>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(TYPES).get()
            .addOnSuccessListener { querySnapshot ->
                val types = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot?.toObject<Type>()
                }
                onSuccess(types)
            }
            .addOnFailureListener { onFailure(it) }
    }
}