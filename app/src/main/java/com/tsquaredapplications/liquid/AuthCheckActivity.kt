package com.tsquaredapplications.liquid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsquaredapplications.liquid.databinding.ActivityAuthCheckBinding

class AuthCheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}