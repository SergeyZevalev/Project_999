package com.example.project_999.core

import android.app.Application

class MyApp : Application(), ProvideRepresentative, ClearRepresentative {

    private lateinit var factory: ProvideRepresentative.Factory
    override fun onCreate() {
        super.onCreate()
        factory = ProvideRepresentative.Factory(Core.Base(this), this)

    }

    override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>): T =
        factory.provideRepresentative(clasz)


    override fun clear(clazz: Class<out Representative<*>>) {
        factory.clear(clazz)
    }


}