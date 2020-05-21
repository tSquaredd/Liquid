package com.tsquaredapplications.liquid.common.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthManager {
    fun loginWith(
        email: String,
        password: String,
        onComplete: (Task<AuthResult>) -> Unit = {},
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun signUpWith(
        email: String,
        password: String,
        onComplete: (Task<AuthResult>) -> Unit = {},
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun sendPasswordReset(
        email: String,
        onComplete: (Task<Void>) -> Unit = {},
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}