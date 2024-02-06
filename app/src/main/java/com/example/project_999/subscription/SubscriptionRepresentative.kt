package com.example.project_999.subscription

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Representative
import com.example.project_999.dashboard.Dashboard
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.Navigation
import com.example.project_999.main.UserPremiumCache

interface SubscriptionRepresentative: Representative<Unit> {

    fun subscribe()


    class Base(
        private val clear: ClearRepresentative,
        private val userPremiumCache: UserPremiumCache.Save,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {
        override fun subscribe() {
            userPremiumCache.saveUserPremium()
            clear.clear(DashboardRepresentative::class.java)
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(Dashboard)
        }

    }
}