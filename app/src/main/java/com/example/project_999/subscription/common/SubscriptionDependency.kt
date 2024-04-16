package com.example.project_999.subscription.common

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Core
import com.example.project_999.core.Module
import com.example.project_999.core.ProvideModule
import com.example.project_999.core.Representative
import com.example.project_999.subscription.progress.SubscriptionProgressModule
import com.example.project_999.subscription.progress.presentation.SubscriptionProgressRepresentative
import com.example.project_999.subscription.screen.SubscriptionModule
import com.example.project_999.subscription.screen.presentation.SubscriptionRepresentative

class SubscriptionDependency(
    private val core: Core,
    private val clear: ClearRepresentative
) : ProvideModule {

    private var scopeModule: SubscriptionScopeModule? = null

    private val provideScopeModule: () -> SubscriptionScopeModule = {
        if (scopeModule == null)
            scopeModule = SubscriptionScopeModule.Base()
        scopeModule!!
    }

    private val clearScopeModule: () -> Unit = {
        clear.clear(SubscriptionRepresentative::class.java)
        clear.clear(SubscriptionProgressRepresentative::class.java)
        scopeModule = null
    }

    override fun <T : Representative<*>> module(clasz: Class<T>) =
        when (clasz) {
            SubscriptionRepresentative::class.java -> SubscriptionModule(
                core,
                clearScopeModule,
                provideScopeModule
            )

            SubscriptionProgressRepresentative::class.java -> SubscriptionProgressModule(
                core,
                provideScopeModule
            )

            else -> throw IllegalStateException("Unknown class $clasz")
        } as Module<T>

}