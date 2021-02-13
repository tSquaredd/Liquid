package com.tsquaredapplications.liquid.history.edit.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateEntryResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) : UpdateEntryResourceWrapper {
    override val invalidAmountErrorMessage: String
        get() = context.getString(R.string.amount_error_message)

}