package com.example.project_999.core

import com.example.project_999.dashboard.DashBoardModule
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.MainModule
import com.example.project_999.main.MainRepresentative
import com.example.project_999.subscription.SubscriptionModule
import com.example.project_999.subscription.SubscriptionRepresentative

interface ProvideRepresentative {

    fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T

    class Factory(
        private val core: Core,
        private val clear: ClearRepresentative
    ) : ProvideRepresentative {
        override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T =
            when (clasz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashBoardModule(core).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(
                    core,
                    clear
                ).representative()

                else -> throw IllegalStateException("Unknown class $clasz")
            } as T

    }
}