package com.tsquaredapplications.liquid.startup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsquaredapplications.liquid.databinding.ActivityStartupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}