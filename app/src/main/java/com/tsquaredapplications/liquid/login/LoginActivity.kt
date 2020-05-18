package com.tsquaredapplications.liquid.login

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.LiquidApplication
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var loginComponent: LoginComponent
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        loginComponent = (applicationContext as LiquidApplication)
            .appComponent.loginComponent().create()
        loginComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.hostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.login_nav_graph)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(WELCOME_SCREEN_TOGGLE_KEY, true)) {
            with(sharedPref.edit()) {
                putBoolean(WELCOME_SCREEN_TOGGLE_KEY, false)
                commit()
            }
            navGraph.startDestination = R.id.welcomeFragment
        } else {
            navGraph.startDestination = R.id.userInformationFragment
        }
        navController.graph = navGraph
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.hostFragment).navigateUp()

    companion object {
        const val WELCOME_SCREEN_TOGGLE_KEY = "shouldShowWelcomeScreen"
    }

}