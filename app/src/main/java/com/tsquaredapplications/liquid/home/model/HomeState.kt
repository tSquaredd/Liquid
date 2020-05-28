package com.tsquaredapplications.liquid.home.model

sealed class HomeState {
    class Initialized(val goalProgress: String) : HomeState()
}