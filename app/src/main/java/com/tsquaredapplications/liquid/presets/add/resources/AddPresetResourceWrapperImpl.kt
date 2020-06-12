package com.tsquaredapplications.liquid.presets.add.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class AddPresetResourceWrapperImpl
@Inject constructor(val context: Context) :
    AddPresetResourceWrapper {

    override val nameErrorMessage: String
        get() = context.getString(R.string.name_error_message)

    override val typeErrorMessage: String
        get() = context.getString(R.string.type_error_message)

    override val amountErrorMessage: String
        get() = context.getString(R.string.size_error_message)
}