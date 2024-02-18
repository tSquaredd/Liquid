@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package dev.tsquaredapps.liquid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import dev.tsquaredapps.liquid.ui.LiquidApp
import dev.tsquaredapps.liquid.ui.theme.LiquidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiquidTheme {
                val windowSize = calculateWindowSizeClass(this)
                LiquidApp(windowSize = windowSize)
            }
        }
    }
}
