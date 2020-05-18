package com.tsquaredapplications.liquid.login

import com.tsquaredapplications.liquid.login.information.UserInformationFragment
import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.login.goal.GoalDisplayFragment
import com.tsquaredapplications.liquid.login.login.EmailLoginFragment
import com.tsquaredapplications.liquid.login.signup.EmailSignUpFragment
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
    fun inject(userInformationFragment: UserInformationFragment)
    fun inject(goalDisplayFragment: GoalDisplayFragment)
}