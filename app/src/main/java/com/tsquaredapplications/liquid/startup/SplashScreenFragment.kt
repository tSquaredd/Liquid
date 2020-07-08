package com.tsquaredapplications.liquid.startup

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.databinding.FragmentSplashScreenBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.startup.SplashScreenFragmentDirections.Companion.toLoginActivity
import com.tsquaredapplications.liquid.startup.SplashScreenFragmentDirections.Companion.toMainActivity
import javax.inject.Inject

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {
    @Inject
    lateinit var userDbManager: UserManager

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as StartupActivity).startupComponent.inject(this)
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
                    when (val userInformation = userDbManager.getUser()) {
                        null -> {
                            navigate(toLoginActivity())
                            activity?.finish()
                        }
                        else -> {
                            (activity as StartupActivity).saveUserInformation(userInformation)
                            navigate(toMainActivity())
                            activity?.finish()
                        }
                    }
                }
            })
            start()
        }
    }
}