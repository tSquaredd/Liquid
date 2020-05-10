package com.tsquaredapplications.liquid.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentSplashScreenBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.splash.SplashScreenFragmentDirections.Companion.toLoginActivity
import com.tsquaredapplications.liquid.splash.SplashScreenFragmentDirections.Companion.toMainActivity
import javax.inject.Inject

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AuthCheckActivity).authCheckComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            addUpdateListener {
                binding.splashText.alpha = it.animatedFraction
                binding.splashText.scaleX = it.animatedFraction
                binding.splashText.scaleY = it.animatedFraction
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    when (auth.currentUser) {
                        null -> navigate(toLoginActivity())
                        else -> navigate(toMainActivity())
                    }
                }
            })
            start()
        }
    }
}