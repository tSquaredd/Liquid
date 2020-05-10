package com.tsquaredapplications.liquid.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsquaredapplications.liquid.LiquidApplication
import com.tsquaredapplications.liquid.databinding.ActivityAuthCheckBinding

class AuthCheckActivity : AppCompatActivity() {

    lateinit var authCheckComponent: AuthCheckComponent
    private lateinit var binding: ActivityAuthCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        authCheckComponent = (applicationContext as LiquidApplication)
            .appComponent.authComponent().create()
        authCheckComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityAuthCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}