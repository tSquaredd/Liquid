package com.tsquaredapplications.liquid.settings.weight

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeightSettingResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) : WeightSettingResourceWrapper {
    override val weightErrorMessage: String
        get() = context.getString(R.string.weight_error_message)
}