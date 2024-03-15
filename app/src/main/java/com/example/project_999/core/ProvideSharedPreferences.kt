package com.example.project_999.core

import android.content.Context
import android.content.SharedPreferences
import com.example.project_999.main.Navigation

interface ProvideSharedPreferences {

    fun sharedPreferences() : SharedPreferences


}

interface ProvideNavigation {

    fun navigation(): Navigation.Mutable
}

interface ProvideRunAsync{

    fun runAsync(): RunAsync
}
interface Core : ProvideNavigation, ProvideSharedPreferences, ProvideRunAsync {
    class Base(private val context: Context) : Core {

        private val navigation = Navigation.Base()
        private var runAsync = RunAsync.Base(DispatchersList.Base())
        override fun navigation(): Navigation.Mutable  = navigation

        override fun sharedPreferences(): SharedPreferences =
             context.getSharedPreferences("project999", Context.MODE_PRIVATE)

        override fun runAsync(): RunAsync = runAsync
    }
}