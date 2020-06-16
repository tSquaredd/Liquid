package com.tsquaredapplications.liquid.startup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsquaredapplications.liquid.LiquidApplication
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.databinding.ActivityStartupBinding

class StartupActivity : AppCompatActivity() {

    lateinit var startupComponent: StartupComponent
    private lateinit var binding: ActivityStartupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        startupComponent = (applicationContext as LiquidApplication)
            .appComponent.startupComponent().create()
        startupComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun saveUserInformation(userInformation: UserInformation) {
        (applicationContext as LiquidApplication).mainModule.userInformation = userInformation
    }
}