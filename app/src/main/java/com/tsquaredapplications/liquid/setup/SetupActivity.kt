package com.tsquaredapplications.liquid.setup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.LiquidApplication
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.databinding.ActivitySetupBinding
import javax.inject.Inject

class SetupActivity : AppCompatActivity() {

    lateinit var setupComponent: SetupComponent
    private lateinit var binding: ActivitySetupBinding

    @Inject lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent = (applicationContext as LiquidApplication)
            .appComponent.setupComponent().create()
        setupComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.hostFragment).navigateUp()

    fun setUserInformation(userInformation: UserInformation) {
        userManager.setUser(userInformation)
    }
}