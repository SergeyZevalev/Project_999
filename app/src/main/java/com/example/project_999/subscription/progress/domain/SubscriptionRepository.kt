package com.example.project_999.subscription.progress.domain

interface SubscriptionRepository {

    fun isUserPremium() : Boolean
    fun subscribe()
    suspend fun subscribeInternal()
}