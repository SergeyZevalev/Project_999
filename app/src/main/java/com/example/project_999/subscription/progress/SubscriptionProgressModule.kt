package com.example.project_999.subscription.progress

import com.example.project_999.core.Core
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Module
import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.common.SubscriptionScopeModule
import com.example.project_999.subscription.progress.data.BaseSubscriptionRepository
import com.example.project_999.subscription.progress.data.SubscriptionCloudDataSource
import com.example.project_999.subscription.progress.data.WorkManagerWrapper
import com.example.project_999.subscription.progress.domain.SubscriptionInteractor
import com.example.project_999.subscription.progress.presentation.SubscriptionProgressObservable
import com.example.project_999.subscription.progress.presentation.SubscriptionProgressRepresentative
import com.example.project_999.subscription.screen.presentation.SubscriptionUiMapper

class SubscriptionProgressModule(
    private val core: Core,
    private val provideScopeModule: () -> SubscriptionScopeModule
) : Module<SubscriptionProgressRepresentative>{

    override fun representative(): SubscriptionProgressRepresentative.Base {
        val observable = SubscriptionProgressObservable.Base()
        return SubscriptionProgressRepresentative.Base(
            observable,
            HandleDeath.Base(),
            core.runAsync(),
            SubscriptionUiMapper(
                provideScopeModule.invoke().provideSubscriptionObservable(),
                observable
                ),
            SubscriptionInteractor.Base(BaseSubscriptionRepository(
                WorkManagerWrapper.Base(core.workManager()),
                SubscriptionCloudDataSource.Base(),
                UserPremiumCache.Base(core.sharedPreferences())
            ))
        )
    }
}