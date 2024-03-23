package com.example.project_999.subscription

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Core
import com.example.project_999.core.DispatchersList
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Module
import com.example.project_999.core.RunAsync
import com.example.project_999.core.UiObserver
import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.data.BaseSubscriptionRepository
import com.example.project_999.subscription.data.SubscriptionCloudDataSource
import com.example.project_999.subscription.data.WorkManagerWrapper
import com.example.project_999.subscription.domain.SubscriptionInteractor
import com.example.project_999.subscription.presentation.SubscriptionObservable
import com.example.project_999.subscription.presentation.SubscriptionRepresentative
import com.example.project_999.subscription.presentation.SubscriptionUiMapper
import com.example.project_999.subscription.presentation.SubscriptionUiState

class SubscriptionModule(
    private val core: Core,
    private val clear: ClearRepresentative
) : Module<SubscriptionRepresentative> {

    private val observable = SubscriptionObservable.Base()
    override fun representative() = SubscriptionRepresentative.Base(
        SubscriptionUiMapper(observable),
        core.runAsync(),
        HandleDeath.Base(),
        observable,
        clear,
        SubscriptionInteractor.Base(
            BaseSubscriptionRepository(
                WorkManagerWrapper.Base(core.workManager()),
                SubscriptionCloudDataSource.Base(),
                UserPremiumCache.Base(core.sharedPreferences())
            )
        ),
        core.navigation()
    )

}