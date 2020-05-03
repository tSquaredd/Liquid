package com.tsquaredapplications.liquid.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.ext.validateEmail
import com.tsquaredapplications.liquid.login.resources.EmailLoginResourceWrapper
import javax.inject.Inject

class EmailLoginViewModel
@Inject constructor(private val resourceWrapper: EmailLoginResourceWrapper) : ViewModel() {

    private val stateLiveData = MutableLiveData<EmailLoginState>()
    internal fun getStateLiveData(): LiveData<EmailLoginState> = stateLiveData

    private var email = ""
    private var password = ""

    fun emailUpdated(email: CharSequence) {
        this.email = email.toString()
        if (!email.validateEmail()) {
            stateLiveData.value =
                EmailLoginState.InvalidEmail(resourceWrapper.getEmailInputErrorText())
        }
    }
}