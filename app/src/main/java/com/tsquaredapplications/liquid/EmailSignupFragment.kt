package com.tsquaredapplications.liquid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tsquaredapplications.liquid.EmailSignupFragmentDirections.Companion.toUserInformationFragment
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentEmailSignupBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.login.EmailSignUpState
import com.tsquaredapplications.liquid.login.EmailSignUpViewModel
import com.tsquaredapplications.liquid.login.EmailValidationState
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.PasswordValidationState
import javax.inject.Inject

class EmailSignupFragment : BaseFragment<FragmentEmailSignupBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: EmailSignUpViewModel by viewModels { viewModelFactory }

    private var emailTextChangeListenerActive = false
    private var passwordTextChangeListenerActive = false

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailSignupBinding = FragmentEmailSignupBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailEditText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputEmail = (v as EditText).text
                if (inputEmail.isNotEmpty()) {
                    viewModel.validateEmail(inputEmail)
                }
            }
        }

        binding.passwordEditText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputPassword = (v as EditText).text
                if (inputPassword.isNotEmpty()) {
                    viewModel.validatePassword(inputPassword)
                }
            }
        }

        binding.signUpButton.setOnClickListener {
            viewModel.onSignUpCLicked(
                binding.emailEditText.text ?: "",
                binding.passwordEditText.text ?: ""
            )
        }

        binding.emailTextInputLayout.isEndIconVisible = false

        viewModel.getEmailValidationLiveData().observe(viewLifecycleOwner, Observer {
            onEmailValidationStateChange(it)
        })

        viewModel.getPasswordValidationLiveData().observe(viewLifecycleOwner, Observer {
            onPasswordValidationStateChange(it)
        })

        viewModel.getStateLiveData().observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })
    }

    private fun onStateChange(state: EmailSignUpState) {
        when (state) {
            is EmailSignUpState.AttemptSignUp -> attemptSignUp(state.email, state.password)
            is EmailSignUpState.SuccessfulSignUp -> onSuccessfulSignUp()
        }
    }

    private fun attemptSignUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.onSignUpSuccess()
                } else {
                    // TODO
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        // TODO USER EXISTS
                    } else {
                        // TODO GENERIC ERROR
                    }
                }
            }
    }

    private fun onSuccessfulSignUp() {
        navigate(toUserInformationFragment())
    }

    // region Email Validation
    private fun onEmailValidationStateChange(state: EmailValidationState?) {
        when (state) {
            is EmailValidationState.Valid -> showValidEmail()
            is EmailValidationState.Invalid -> showInvalidEmail(state.errorMessage)
        }
    }

    private fun showValidEmail() {
        binding.emailTextInputLayout.error = null
        binding.emailTextInputLayout.isEndIconVisible = true
        setEmailTextChangeListener()
    }

    private fun showInvalidEmail(errorMessage: String) {
        binding.emailTextInputLayout.error = errorMessage
        setEmailTextChangeListener()
    }

    private fun setEmailTextChangeListener() {
        binding.emailEditText.onFocusChangeListener = null

        if (!emailTextChangeListenerActive) {
            emailTextChangeListenerActive = true
            binding.emailEditText.addTextChangedListener { editable ->
                viewModel.validateEmail(editable.toString())
            }
        }
    }
    // endregion

    //region password validation
    private fun onPasswordValidationStateChange(state: PasswordValidationState?) {
        when (state) {
            is PasswordValidationState.Valid -> showValidPassword()
            is PasswordValidationState.Invalid -> showInvalidPassword(state.errorMessage)
        }
    }

    private fun showValidPassword() {
        binding.passwordTextInputLayout.error = null
        setPasswordTextChangeListener()
    }

    private fun showInvalidPassword(errorMessage: String) {
        binding.passwordTextInputLayout.error = errorMessage
        setPasswordTextChangeListener()
    }

    private fun setPasswordTextChangeListener() {
        binding.passwordEditText.onFocusChangeListener = null

        if (!passwordTextChangeListenerActive) {
            passwordTextChangeListenerActive = true
            binding.passwordEditText.addTextChangedListener { editable ->
                viewModel.validatePassword(editable.toString())
            }
        }
    }
    //endregion
}
