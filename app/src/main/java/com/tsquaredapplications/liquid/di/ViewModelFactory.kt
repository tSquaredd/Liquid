package com.tsquaredapplications.liquid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.add.amount.DrinkAmountViewModel
import com.tsquaredapplications.liquid.add.drink.SelectDrinkViewModel
import com.tsquaredapplications.liquid.home.HomeViewModel
import com.tsquaredapplications.liquid.presets.add.AddPresetViewModel
import com.tsquaredapplications.liquid.presets.edit.EditPresetViewModel
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionViewModel
import com.tsquaredapplications.liquid.presets.main.PresetsViewModel
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionViewModel
import com.tsquaredapplications.liquid.settings.SettingsViewModel
import com.tsquaredapplications.liquid.settings.goal.GoalSettingViewModel
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingsViewModel
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingViewModel
import com.tsquaredapplications.liquid.settings.weight.WeightSettingViewModel
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayViewModel
import com.tsquaredapplications.liquid.setup.information.UserInformationViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory
@Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Module
    abstract class ViewModelModule {

        @Binds
        internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ViewModelKey(UserInformationViewModel::class)
        internal abstract fun bindUserInformationViewModel(viewModel: UserInformationViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(GoalDisplayViewModel::class)
        internal abstract fun bindGoalDisplayViewModel(viewModel: GoalDisplayViewModel): ViewModel


        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(AddPresetViewModel::class)
        internal abstract fun bindAddPresetViewModel(viewModel: AddPresetViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(PresetTypeSelectionViewModel::class)
        internal abstract fun bindPresetTypeSelectionViewModel(viewModel: PresetTypeSelectionViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(PresetIconSelectionViewModel::class)
        internal abstract fun bindPresetIconSelectionViewModel(viewModel: PresetIconSelectionViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(PresetsViewModel::class)
        internal abstract fun bindPresetsViewModel(viewModel: PresetsViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(EditPresetViewModel::class)
        internal abstract fun bindEditPresetViewModel(viewModel: EditPresetViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(SettingsViewModel::class)
        internal abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(GoalSettingViewModel::class)
        internal abstract fun bindGoalSettingViewModel(viewModel: GoalSettingViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(SelectDrinkViewModel::class)
        internal abstract fun bindSelectDrinkViewModel(viewModel: SelectDrinkViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(DrinkAmountViewModel::class)
        internal abstract fun bindDrinkAmountViewModel(viewModel: DrinkAmountViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(LiquidUnitSettingViewModel::class)
        internal abstract fun bindLiquidUnitSettingViewModel(viewModel: LiquidUnitSettingViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(WeightSettingViewModel::class)
        internal abstract fun bindWeightSettingViewModel(viewModel: WeightSettingViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(NotificationsSettingsViewModel::class)
        internal abstract fun bindNotificationSettingsViewModel(viewModel: NotificationsSettingsViewModel): ViewModel
    }
}