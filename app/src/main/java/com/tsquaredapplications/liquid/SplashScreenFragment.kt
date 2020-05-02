package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tsquaredapplications.liquid.SplashScreenFragmentDirections.Companion.toLoginActivity
import com.tsquaredapplications.liquid.SplashScreenFragmentDirections.Companion.toMainActivity
import com.tsquaredapplications.liquid.databinding.FragmentSplashScreenBinding
import com.tsquaredapplications.liquid.ext.navigate

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {

    private lateinit var auth: FirebaseAuth

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.initialize(requireContext())
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        when (auth.currentUser) {
            null -> navigate(toLoginActivity())
            else -> navigate(toMainActivity())
        }
    }

}