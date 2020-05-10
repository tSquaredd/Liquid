package com.tsquaredapplications.liquid.login.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.common.PasswordValidation
import com.tsquaredapplications.liquid.login.login.resources.EmailLoginResourceWrapper
import javax.inject.Inject

class EmailLoginViewModel
@Inject constructor(
    private val auth: FirebaseAuth,
    private val resourceWrapper: EmailLoginResourceWrapper
) : ViewModel() {

    private val stateLiveData = SingleEventLiveData<EmailLoginState>()
    internal fun getStateLiveData(): LiveData<EmailLoginState> = stateLiveData

    private val loginButtonEnabledLiveData = SingleEventLiveData<Boolean>().apply { value = false }
    fun getLoginButtonEnabledLiveData(): LiveData<Boolean> = loginButtonEnabledLiveData

    private var emailEntered = false
    private var passwordEntered = false

    fun onLoginClicked(email: CharSequence, password: CharSequence) {
        if (!email.isValidEmail() || password.isValidPassword() != PasswordValidation.Valid) {
            onFailedLogin()
        } else {
            stateLiveData.value =
                EmailLoginState.ShowProgressBar
            auth.signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener { task ->
                    onSignInResult(task)
                }
        }
    }

    @VisibleForTesting
    fun onSignInResult(task: Task<AuthResult>) {
        stateLiveData.value =
            EmailLoginState.HideProgressBar
        if (task.isSuccessful) {
            onSuccessfulLogin()
        } else {
            onFailedLogin()
        }
    }

    private fun onSuccessfulLogin() {
        stateLiveData.value =
            EmailLoginState.SuccessFulLogin
    }

    private fun onFailedLogin() {
        stateLiveData.value =
            EmailLoginState.FailedLogin(
                errorMessage = resourceWrapper.getFailedLoginErrorMessage(),
                dismissButtonText = resourceWrapper.getFailedLoginErrorDismissButtonText()
            )
    }

    fun emailUpdated(email: String) {
        emailEntered = email.isNotEmpty()
        checkToEnableSignInButton()
    }

    fun passwordUpdated(password: String) {
        passwordEntered = password.isNotEmpty()
        checkToEnableSignInButton()
    }

    private fun checkToEnableSignInButton() {
        loginButtonEnabledLiveData.value = emailEntered && passwordEntered
    }
}