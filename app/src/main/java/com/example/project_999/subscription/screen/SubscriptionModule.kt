package com.example.project_999.subscription.screen

import com.example.project_999.core.Core
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Module
import com.example.project_999.subscription.common.SubscriptionScopeModule
import com.example.project_999.subscription.screen.presentation.SubscriptionRepresentative

class SubscriptionModule(
    private val core: Core,
    private val clear: () -> Unit,
    private val provideScopeModule: () -> SubscriptionScopeModule
) : Module<SubscriptionRepresentative> {

    override fun representative() = SubscriptionRepresentative.Base(
        HandleDeath.Base(),
        provideScopeModule.invoke().provideSubscriptionObservable(),
        clear,
        core.navigation()
    )

}