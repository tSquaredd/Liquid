package com.tsquaredapplications.liquid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tsquaredapplications.liquid.common.database.icons.PresetIconDatabaseManager
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDatabaseManager
import com.tsquaredapplications.liquid.common.database.types.TypeDatabaseManager
import com.tsquaredapplications.liquid.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var typeDatabaseManager: TypeDatabaseManager

    @Inject
    lateinit var presetIconDatabaseManager: PresetIconDatabaseManager

    @Inject
    lateinit var presetDatabaseManager: PresetDatabaseManager

    lateinit var mainComponent: MainComponent
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        with(applicationContext as LiquidApplication) {
            mainComponent = appComponent.mainComponent().create()
        }

        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.hostFragment)
        setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(navController)

        getTypes()
        getPresetIcons()
        getUserPresets()
    }

    private fun getTypes() {
        typeDatabaseManager.getTypes(
            onSuccess = { typeList ->
                (applicationContext as LiquidApplication).mainModule.drinkTypes = typeList
            },
            onFailure = {
                // TODO HANDLE TYPE LOAD FAILURE LIQ-124
            }
        )
    }

    private fun getPresetIcons() {
        presetIconDatabaseManager.getPresetIcons(
            onSuccess = { presetIconList ->
                (applicationContext as LiquidApplication).mainModule.presetIcons = presetIconList
            },
            onFailure = {
                // TODO HANDLE PRESET ICON LOAD FAILURE LIQ-124
            }
        )
    }

    private fun getUserPresets() {
        presetDatabaseManager.getAllPresets(
            onSuccess = { presetsList ->
                (applicationContext as LiquidApplication).mainModule.initializePresets(presetsList)
            },
            onFailure = {
                // TODO HANDLE PRESETS LOAD FAILURE LIQ-124
            }
        )
    }

    fun addPreset(preset: Preset) {
        (applicationContext as LiquidApplication).mainModule.addPreset(preset)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.hostFragment).navigateUp()
}
