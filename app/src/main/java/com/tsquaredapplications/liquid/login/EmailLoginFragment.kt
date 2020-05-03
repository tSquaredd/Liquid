package com.tsquaredapplications.liquid.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentEmailLoginBinding
import javax.inject.Inject

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EmailLoginViewModel by viewModels { viewModelFactory }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEmailLoginBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            viewModel.emailUpdated("")
        }

        viewModel.getStateLiveData().observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })
    }

    private fun onStateChange(state: EmailLoginState?) {
        when (state) {
            is EmailLoginState.InvalidEmail -> showInvalidEmail(state.errorMessage)
        }
    }

    private fun showInvalidEmail(errorMessage: String) {
        binding.emailEditText.error = errorMessage
    }
}