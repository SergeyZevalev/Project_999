package com.example.project_999.subscription.data

import kotlinx.coroutines.delay

interface SubscriptionCloudDataSource {

    suspend fun subscribe()

    class Base(): SubscriptionCloudDataSource {
        override suspend fun subscribe() {
            delay(5000)
        }

    }
}