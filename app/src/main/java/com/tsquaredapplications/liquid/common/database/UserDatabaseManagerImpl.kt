package com.tsquaredapplications.liquid.common.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tsquaredapplications.liquid.common.USERS
import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import javax.inject.Inject

class UserDatabaseManagerImpl
@Inject constructor(private val db: FirebaseFirestore, private val auth: FirebaseAuth) :
    UserDatabaseManager {
    override fun setUser(
        userInformation: UserInformation,
        onCompletion: () -> Unit,
        onFail: () -> Unit,
        onSuccess: () -> Unit
    ) {
        val userId = auth.uid
        if (userId.isNullOrEmpty()) {
            onFail()
        } else {
            db.collection(USERS).document(userId).set(userInformation)
                .addOnCompleteListener {
                    onCompletion()
                }
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFail()
                }
        }
    }
}