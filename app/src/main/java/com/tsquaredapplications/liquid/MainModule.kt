package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.add.amount.resources.DrinkAmountResourceWrapper
import com.tsquaredapplications.liquid.add.amount.resources.DrinkAmountResourceWrapperImpl
import com.tsquaredapplications.liquid.add.drink.SelectDrinkResourceWrapper
import com.tsquaredapplications.liquid.add.drink.SelectDrinkResourceWrapperImpl
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.history.day.DayHistoryResourceWrapper
import com.tsquaredapplications.liquid.history.day.DayHistoryResourceWrapperImpl
import com.tsquaredapplications.liquid.history.edit.resources.UpdateEntryResourceWrapper
import com.tsquaredapplications.liquid.history.edit.resources.UpdateEntryResourceWrapperImpl
import com.tsquaredapplications.liquid.history.main.resources.HistoryResourceWrapper
import com.tsquaredapplications.liquid.history.main.resources.HistoryResourceWrapperImpl
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapper
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapperImpl
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapper
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapperImpl
import com.tsquaredapplications.liquid.settings.goal.resources.GoalSettingResourceWrapper
import com.tsquaredapplications.liquid.settings.goal.resources.GoalSettingResourceWrapperImpl
import com.tsquaredapplications.liquid.settings.notifications.NotificationSettingsResourceWrapper
import com.tsquaredapplications.liquid.settings.notifications.NotificationSettingsResourceWrapperImpl
import com.tsquaredapplications.liquid.settings.resources.SettingsResourceWrapper
import com.tsquaredapplications.liquid.settings.resources.SettingsResourceWrapperImpl
import com.tsquaredapplications.liquid.settings.weight.WeightSettingResourceWrapper
import com.tsquaredapplications.liquid.settings.weight.WeightSettingResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule {

    lateinit var userInformation: UserInformation

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideAddPresetResourceWrapper(impl: AddPresetResourceWrapperImpl)
            : AddPresetResourceWrapper = impl

    @Provides
    fun provideEditPresetResourceWrapper(impl: EditPresetResourceWrapperImpl)
            : EditPresetResourceWrapper = impl

    @Provides
    fun provideSettingsResourceWrapper(impl: SettingsResourceWrapperImpl)
            : SettingsResourceWrapper = impl

    @Provides
    fun provideGoalSettingResourceWrapper(impl: GoalSettingResourceWrapperImpl)
            : GoalSettingResourceWrapper = impl

    @Provides
    fun provideDrinkAmountResourceWrapper(impl: DrinkAmountResourceWrapperImpl)
            : DrinkAmountResourceWrapper = impl

    @Provides
    fun provideSelectDrinkResourceWrapper(impl: SelectDrinkResourceWrapperImpl)
            : SelectDrinkResourceWrapper = impl

    @Provides
    fun provideWeightSettingResourceWrapper(impl: WeightSettingResourceWrapperImpl)
            : WeightSettingResourceWrapper = impl

    @Provides
    fun provideNotificationSettingsResourceWrapper(impl: NotificationSettingsResourceWrapperImpl)
            : NotificationSettingsResourceWrapper = impl

    @Provides
    fun provideHistoryResourceWrapper(impl: HistoryResourceWrapperImpl)
            : HistoryResourceWrapper = impl

    @Provides
    fun provideUpdateEntryResourceWrapper(impl: UpdateEntryResourceWrapperImpl)
            : UpdateEntryResourceWrapper = impl

    @Provides
    fun provideDayHistoryResourceWrapper(impl: DayHistoryResourceWrapperImpl)
            : DayHistoryResourceWrapper = impl
}