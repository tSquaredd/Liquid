package com.tsquaredapplications.liquid.login

import com.tsquaredapplications.liquid.login.signup.EmailSignUpFragment
import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.login.login.EmailLoginFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(loginActivity: LoginActivity)
    fun inject(welcomeFragment: WelcomeFragment)
    fun inject(emailLoginFragment: EmailLoginFragment)
    fun inject(emailSignUpFragment: EmailSignUpFragment)
}