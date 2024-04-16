package com.example.project_999.subscription.progress.data

import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.progress.domain.SubscriptionRepository

class BaseSubscriptionRepository(
    private val workManagerWrapper: WorkManagerWrapper,
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCache: UserPremiumCache.Mutable
) : SubscriptionRepository {

    override fun isUserPremium() = userPremiumCache.isUserPremium()

    override fun subscribe() = workManagerWrapper.start()

    override suspend fun subscribeInternal() {
        cloudDataSource.subscribe()
        userPremiumCache.saveUserPremium()
    }
}