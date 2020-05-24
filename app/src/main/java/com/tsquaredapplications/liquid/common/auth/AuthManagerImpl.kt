package com.tsquaredapplications.liquid.common.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthManagerImpl
@Inject constructor(private val auth: FirebaseAuth) : AuthManager {

    override fun loginWith(
        email: String,
        password: String,
        onComplete: (Task<AuthResult>) -> Unit,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task)
            }
            .addOnSuccessListener { authResult ->
                onSuccess(authResult)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun signUpWith(
        email: String,
        password: String,
        onComplete: (Task<AuthResult>) -> Unit,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task)
            }
            .addOnSuccessListener { authResult ->
                onSuccess(authResult)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun sendPasswordReset(
        email: String,
        onComplete: (Task<Void>) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                onComplete(task)
            }
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)

            }
    }
}