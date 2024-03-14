package com.example.project_999.subscription.data

import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.domain.SubscriptionRepository

class BaseSubscriptionRepository(
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCache: UserPremiumCache.Save
): SubscriptionRepository {
    override suspend fun subscribe() {
        cloudDataSource.subscribe()
        userPremiumCache.saveUserPremium()
    }
}