package com.tsquaredapplications.liquid.login.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.ErrorDialogFragment
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.databinding.FragmentEmailLoginBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisibile
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.login.EmailLoginFragmentDirections.Companion.toEmailSignupFragment
import com.tsquaredapplications.liquid.login.login.EmailLoginFragmentDirections.Companion.toMainActivity
import com.tsquaredapplications.liquid.login.login.EmailLoginFragmentDirections.Companion.toUserInformationFragment
import com.tsquaredapplications.liquid.login.login.EmailLoginState.AbandonedSignUp
import com.tsquaredapplications.liquid.login.login.EmailLoginState.FailedLogin
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ForgotPassword
import com.tsquaredapplications.liquid.login.login.EmailLoginState.HideProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ShowProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.SuccessFulLogin
import javax.inject.Inject

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EmailLoginViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailLoginBinding =
        FragmentEmailLoginBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text ?: ""
            val password = binding.passwordEditText.text ?: ""
            viewModel.onLoginClicked(email, password)
        }

        binding.signUpTextButton.setOnClickListener {
            navigate(toEmailSignupFragment())
        }

        binding.forgotPasswordButton.setOnClickListener {
            viewModel.forgotPasswordClicked()
        }

        binding.emailEditText.addTextChangedListener {
            viewModel.emailUpdated(it.toString())
        }

        binding.passwordEditText.addTextChangedListener {
            viewModel.passwordUpdated(it.toString())
        }

        viewModel.loginButtonEnabledLiveData.observe(viewLifecycleOwner, Observer {
            onLoginButtonEnabledStateChange(it)
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })
    }

    private fun onStateChange(state: EmailLoginState?) {
        when (state) {
            is SuccessFulLogin -> onSuccessfulLogin()
            is FailedLogin -> onFailedLogin(state)
            is ForgotPassword -> showForgotPasswordDialog(state.authManager)
            is AbandonedSignUp -> navigate(toUserInformationFragment())
            is ShowProgressBar -> showProgressBar()
            is HideProgressBar -> hideProgressBar()
        }
    }

    private fun onLoginButtonEnabledStateChange(it: Boolean) {
        binding.loginButton.isEnabled = it
    }

    private fun onSuccessfulLogin() {
        navigate(toMainActivity())
    }

    private fun onFailedLogin(state: FailedLogin) {
        ErrorDialogFragment(state.errorMessage, state.dismissButtonText)
            .show(parentFragmentManager, null)
    }

    private fun showForgotPasswordDialog(authManager: AuthManager) {
        ForgotPasswordDialog(authManager)
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
}