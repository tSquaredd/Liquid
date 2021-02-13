package com.tsquaredapplications.liquid.home.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomeResourceWrapperImpl
@Inject constructor(
    @ApplicationContext val context: Context,
    val userInformation: UserInformation
) : HomeResourceWrapper {

    override val hydratingText: String =
        context.getString(
            R.string.hydrating_text,
            context.getString(
                if (userInformation.unitPreference == LiquidUnit.OZ) {
                    R.string.ozs
                } else {
                    R.string.mls
                }
            )
        )

    override val dehydratingText: String =
        context.getString(
            R.string.dehydrating_text,
            context.getString(
                if (userInformation.unitPreference == LiquidUnit.OZ) {
                    R.string.ozs
                } else {
                    R.string.mls
                }
            )
        )
}