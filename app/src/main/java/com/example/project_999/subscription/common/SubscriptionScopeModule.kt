package com.example.project_999.subscription.common

import com.example.project_999.subscription.screen.presentation.SubscriptionObservable

interface SubscriptionScopeModule {

    fun provideSubscriptionObservable() : SubscriptionObservable

    class Base : SubscriptionScopeModule {

        private val observable = SubscriptionObservable.Base()

        override fun provideSubscriptionObservable() = observable

    }
}