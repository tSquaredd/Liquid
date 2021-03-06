package com.tsquaredapplications.liquid.presets.edit.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EditPresetResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) : EditPresetResourceWrapper {
    override val amountErrorMessage: String
        get() = context.getString(R.string.amount_error_message)

    override val nameErrorMessage: String
        get() = context.getString(R.string.name_error_message)

    override val deleteFailureMessage: String
        get() = context.getString(R.string.delete_preset_failure)

    override val updateFailureMessage: String
        get() = context.getString(R.string.update_preset_failure)

    override val failureDismissText: String
        get() = context.getString(R.string.got_it)
}