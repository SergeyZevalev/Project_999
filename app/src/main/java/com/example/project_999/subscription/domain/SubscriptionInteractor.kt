package com.example.project_999.subscription.domain

interface SubscriptionInteractor {

    suspend fun subscribe()
    class Base(
        private val repository: SubscriptionRepository
    ) : SubscriptionInteractor {
        override suspend fun subscribe() {
            repository.subscribe()
        }
    }
}