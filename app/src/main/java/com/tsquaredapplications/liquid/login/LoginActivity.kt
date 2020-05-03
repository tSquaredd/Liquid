package com.tsquaredapplications.liquid.login

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
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.hostFragment).navigateUp()

}