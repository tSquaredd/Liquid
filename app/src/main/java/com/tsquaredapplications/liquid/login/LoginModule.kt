package com.tsquaredapplications.liquid.login

import com.tsquaredapplications.liquid.login.information.resources.UserInformationResourceWrapper
import com.tsquaredapplications.liquid.login.information.resources.UserInformationResourceWrapperImpl
import com.tsquaredapplications.liquid.login.login.resources.EmailLoginResourceWrapper
import com.tsquaredapplications.liquid.login.login.resources.EmailLoginResourceWrapperImpl
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapper
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapperImpl
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

    @Provides
    fun providesUserInformationResourceWrapper(impl: UserInformationResourceWrapperImpl)
            : UserInformationResourceWrapper = impl
}