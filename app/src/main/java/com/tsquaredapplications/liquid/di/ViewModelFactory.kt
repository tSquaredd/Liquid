package com.tsquaredapplications.liquid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.login.goal.GoalDisplayViewModel
import com.tsquaredapplications.liquid.login.information.UserInformationViewModel
import com.tsquaredapplications.liquid.login.login.EmailLoginViewModel
import com.tsquaredapplications.liquid.login.signup.EmailSignUpViewModel
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
        @ViewModelKey(EmailLoginViewModel::class)
        internal abstract fun bindEmailLoginViewModel(viewModel: EmailLoginViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(EmailSignUpViewModel::class)
        internal abstract fun bindEmailSignUpViewModel(viewModel: EmailSignUpViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(UserInformationViewModel::class)
        internal abstract fun bindUserInformationViewModel(viewModel: UserInformationViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(GoalDisplayViewModel::class)
        internal abstract fun bindGoalDisplayViewModel(viewModel: GoalDisplayViewModel): ViewModel
        //Add more ViewModels here
    }
}