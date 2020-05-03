package com.tsquaredapplications.liquid.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentSignInOptionsBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.login.SignInOptionsFragmentDirections.Companion.toEmailLoginFragment

class SignInOptionsFragment : BaseFragment<FragmentSignInOptionsBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInOptionsBinding =
        FragmentSignInOptionsBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailButton.setOnClickListener {
            navigate(toEmailLoginFragment())
        }
    }
}