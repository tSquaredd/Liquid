package com.tsquaredapplications.liquid.login

import com.tsquaredapplications.liquid.login.resources.EmailLoginResourceWrapper
import com.tsquaredapplications.liquid.login.resources.EmailLoginResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [LoginComponent::class])
class LoginModule {

    @Provides
    fun providesEmailLoginResourceWrapper(impl: EmailLoginResourceWrapperImpl)
            : EmailLoginResourceWrapper = impl

    @Provides
    fun providesEmailSignUpResourceWrapper(impl: EmailSignUpResourceWrapperImpl)
            : EmailSignUpResourceWrapper = impl
}