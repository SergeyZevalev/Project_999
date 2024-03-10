package com.example.project_999.core

import com.example.project_999.dashboard.DashBoardModule
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.MainModule
import com.example.project_999.main.MainRepresentative
import com.example.project_999.subscription.SubscriptionModule
import com.example.project_999.subscription.presentation.SubscriptionRepresentative

interface ProvideRepresentative {

    fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T

    class MakeDependency(
        private val core: Core,
        private val clear: ClearRepresentative
    ) : ProvideRepresentative{
        override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T =
            when (clasz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashBoardModule(core, clear).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(
                    core,
                    clear
                ).representative()

                else -> throw IllegalStateException("Unknown class $clasz")
            } as T


    }

    class Factory(
        private val core: Core,
        private val clear: ClearRepresentative,
        private val makeDependency: ProvideRepresentative = MakeDependency(core, clear)
    ) : ProvideRepresentative, ClearRepresentative {

        private val representativeMap = mutableMapOf<Class<out Representative<*>>, Representative<*>>()
        override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T =
            if (representativeMap.containsKey(clasz)) {
                representativeMap[clasz] as T
            } else {
                val representative = makeDependency.provideRepresentative(clasz)
                representativeMap[clasz] = representative
                representative
            }

        override fun clear(clazz: Class<out Representative<*>>) {
            representativeMap.remove(clazz)
        }

    }
}