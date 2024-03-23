package com.example.project_999.core

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.project_999.main.Navigation

interface ProvideSharedPreferences {

    fun sharedPreferences(): SharedPreferences


}

interface ProvideNavigation {

    fun navigation(): Navigation.Mutable
}

interface ProvideRunAsync {

    fun runAsync(): RunAsync
}

interface ProvideWorkManager {

    fun workManager(): WorkManager
}

interface Core : ProvideNavigation, ProvideSharedPreferences, ProvideRunAsync, ProvideWorkManager {
    class Base(private val context: Context) : Core {

        private val navigation = Navigation.Base()
        private val runAsync by lazy { RunAsync.Base(DispatchersList.Base()) }
        private val workManager by lazy { WorkManager.getInstance(context) }
        override fun navigation(): Navigation.Mutable = navigation

        override fun sharedPreferences(): SharedPreferences =
            context.getSharedPreferences("project999", Context.MODE_PRIVATE)

        override fun runAsync(): RunAsync = runAsync
        override fun workManager() = workManager
    }
}