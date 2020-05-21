package com.tsquaredapplications.liquid.login.signup

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
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.ErrorDialogFragment
import com.tsquaredapplications.liquid.databinding.FragmentEmailSignupBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisibile
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.common.EmailValidationState
import com.tsquaredapplications.liquid.login.common.PasswordValidationState
import com.tsquaredapplications.liquid.login.signup.EmailSignUpFragmentDirections.Companion.toUserInformationFragment
import javax.inject.Inject

class EmailSignUpFragment : BaseFragment<FragmentEmailSignupBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

        viewModel.emailValidationLiveData.observe(viewLifecycleOwner, Observer {
            onEmailValidationStateChange(it)
        })

        viewModel.passwordValidationLiveData.observe(viewLifecycleOwner, Observer {
            onPasswordValidationStateChange(it)
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })
    }

    private fun onStateChange(state: EmailSignUpState) {
        when (state) {
            is EmailSignUpState.SuccessfulSignUp -> onSuccessfulSignUp()
            is EmailSignUpState.FailedSignUp -> onFailedSignUp(
                state.errorMessage,
                state.dismissButtonText
            )
            is EmailSignUpState.ShowProgressBar -> showProgressBar()
            is EmailSignUpState.HideProgressBar -> hideProgressBar()
        }
    }

    private fun onSuccessfulSignUp() {
        navigate(toUserInformationFragment())
    }

    private fun onFailedSignUp(errorMessage: String, dismissButtonText: String) {
        ErrorDialogFragment(errorMessage, dismissButtonText)
            .show(parentFragmentManager, null)
    }

    private fun showProgressBar() {
        binding.progressBar.setAsVisibile()
        binding.loadingMask.setAsVisibile()
    }

    private fun hideProgressBar() {
        binding.progressBar.setAsGone()
        binding.loadingMask.setAsGone()
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
