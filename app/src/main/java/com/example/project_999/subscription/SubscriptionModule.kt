package com.example.project_999.subscription

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Core
import com.example.project_999.core.DispatchersList
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Module
import com.example.project_999.core.RunAsync
import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.data.BaseSubscriptionRepository
import com.example.project_999.subscription.data.SubscriptionCloudDataSource
import com.example.project_999.subscription.domain.SubscriptionInteractor
import com.example.project_999.subscription.presentation.SubscriptionObservable
import com.example.project_999.subscription.presentation.SubscriptionRepresentative

class SubscriptionModule(
    private val core: Core,
    private val clear: ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative() = SubscriptionRepresentative.Base(
        core.runAsync(),
        HandleDeath.Base(),
        SubscriptionObservable.Base(),
        clear,
        SubscriptionInteractor.Base(
            BaseSubscriptionRepository(
                SubscriptionCloudDataSource.Base(),
                UserPremiumCache.Base(core.sharedPreferences())
            )
        ),
        core.navigation()
    )

}