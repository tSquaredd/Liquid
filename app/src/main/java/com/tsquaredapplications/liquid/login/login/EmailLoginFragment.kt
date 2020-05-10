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
import com.tsquaredapplications.liquid.databinding.FragmentEmailLoginBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisibile
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.login.EmailLoginFragmentDirections.Companion.toEmailSignupFragment
import com.tsquaredapplications.liquid.login.login.EmailLoginFragmentDirections.Companion.toMainActivity
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
            // TODO NEED PASSWORD RESET DIALOG
        }

        binding.emailEditText.addTextChangedListener {
            viewModel.emailUpdated(it.toString())
        }

        binding.passwordEditText.addTextChangedListener {
            viewModel.passwordUpdated(it.toString())
        }

        viewModel.getLoginButtonEnabledLiveData().observe(viewLifecycleOwner, Observer {
            onLoginButtonEnabledStateChange(it)
        })

        viewModel.getStateLiveData().observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })
    }

    private fun onStateChange(state: EmailLoginState?) {
        when (state) {
            is EmailLoginState.SuccessFulLogin -> onSuccessfulLogin()
            is EmailLoginState.FailedLogin -> onFailedLogin(state)
            is EmailLoginState.ShowProgressBar -> showProgressBar()
            is EmailLoginState.HideProgressBar -> hideProgressBar()
        }
    }

    private fun onLoginButtonEnabledStateChange(it: Boolean) {
        binding.loginButton.isEnabled = it
    }

    private fun onSuccessfulLogin() {
        navigate(toMainActivity())
    }

    private fun onFailedLogin(state: EmailLoginState.FailedLogin) {
        ErrorDialogFragment(state.errorMessage, state.dismissButtonText)
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