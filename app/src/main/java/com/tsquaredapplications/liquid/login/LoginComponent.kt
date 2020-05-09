package com.tsquaredapplications.liquid.login

import com.tsquaredapplications.liquid.EmailSignupFragment
import com.tsquaredapplications.liquid.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(loginActivity: LoginActivity)
    fun inject(signInOptionsFragment: SignInOptionsFragment)
    fun inject(emailLoginFragment: EmailLoginFragment)
    fun inject(emailSignupFragment: EmailSignupFragment)
}