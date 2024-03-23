package com.example.project_999.subscription.domain

interface SubscriptionRepository {

    fun isUserPremium() : Boolean
    fun subscribe()

    suspend fun subscribeInternal()
}