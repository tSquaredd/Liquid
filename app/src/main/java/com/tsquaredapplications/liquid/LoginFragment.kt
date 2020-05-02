package com.tsquaredapplications.liquid

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

}