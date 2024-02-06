package com.example.project_999.core

import android.app.Application
import com.example.project_999.dashboard.DashBoardModule
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.MainModule
import com.example.project_999.main.MainRepresentative
import com.example.project_999.main.Screen
import com.example.project_999.subscription.SubscriptionModule
import com.example.project_999.subscription.SubscriptionRepresentative

class MyApp : Application(), ProvideRepresentative, ClearRepresentative {

    private val representativeMap = mutableMapOf<Class<out Representative<*>>, Representative<*>>()

    private lateinit var core: Core
    private lateinit var factory: ProvideRepresentative.Factory
    override fun onCreate() {
        super.onCreate()
        core = Core.Base(this)
        factory = ProvideRepresentative.Factory(core, this)
    }

    override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T =
        if (representativeMap.containsKey(clasz)) {
            representativeMap[clasz] as T
        } else {
            factory.provideRepresentative(clasz).let {
                representativeMap[clasz] = it
                it
            }

        }


    override fun clear(clazz: Class<out Representative<*>>) {
        representativeMap.remove(clazz)
    }


}