package com.example.project_999.core

import com.example.project_999.dashboard.DashBoardModule
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.MainModule
import com.example.project_999.main.MainRepresentative
import com.example.project_999.subscription.common.SubscriptionDependency

interface ProvideRepresentative {

    fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T

    class MakeDependency(
        private val core: Core,
        private val clear: ClearRepresentative,
        private val provideModule: ProvideModule
    ) : ProvideModule {

        override fun <T : Representative<*>> module(clasz: Class<T>) =
            when (clasz) {
                MainRepresentative::class.java -> MainModule(core)
                DashboardRepresentative::class.java -> DashBoardModule(core, clear)
                else -> provideModule.module(clasz)
            } as Module<T>
    }

    class Factory(
        core: Core,
        clear: ClearRepresentative
    ) : ProvideRepresentative, ClearRepresentative {

        private val makeDependency: ProvideModule =
            MakeDependency(
                core,
                clear,
                SubscriptionDependency(core, clear)
            )

        private val representativeMap =
            mutableMapOf<Class<out Representative<*>>, Representative<*>>()

        override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>) =
            if (representativeMap.containsKey(clasz)) {
                representativeMap[clasz] as T
            } else {
                val representative = makeDependency.module(clasz).representative()
                representativeMap[clasz] = representative
                representative
            }

        override fun clear(clazz: Class<out Representative<*>>) {
            representativeMap.remove(clazz)
        }

    }
}

interface ProvideModule{

    fun <T: Representative<*>> module(clasz: Class<T>) : Module<T>
}