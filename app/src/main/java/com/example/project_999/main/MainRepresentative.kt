package com.example.project_999.main

import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.Dashboard

interface MainRepresentative: Representative<Screen> {
    fun showDashboard(firstTime: Boolean)
    fun saveState()

    class Base(
        private val navigation: Navigation.Mutable
    ) : MainRepresentative {


        override fun startGettingUpdates(callback: UiObserver<Screen>) =
            navigation.updateObserver(callback)

        override fun stopGettingUpdates() = navigation.updateObserver()

        override fun showDashboard(firstTime: Boolean) {
            if (firstTime) {
                navigation.update(Dashboard)
            }
        }

        override fun saveState() {
            //TODO: save and restore
        }

    }
}

