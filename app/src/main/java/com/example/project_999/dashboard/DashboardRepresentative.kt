package com.example.project_999.dashboard

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver
import com.example.project_999.main.Navigation
import com.example.project_999.subscription.presentation.Subscription

interface DashboardRepresentative : Representative<PremiumDashboardUiState> {

    fun observed() = Unit
    fun play()

    class Premium(
        private val observable: PremiumDashBoardObservable
    ) : DashboardRepresentative {

        override fun observed() = observable.clear()
        override fun play() {
            observable.update(PremiumDashboardUiState.Playing)
        }

        override fun startGettingUpdates(callback: UiObserver<PremiumDashboardUiState>) {
            observable.updateObserver(callback)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
        }

        override fun equals(other: Any?)=
            javaClass.name.equals(other?.javaClass?.name)
    }

    class Base(
        private val navigation: Navigation.Update,
        private val clear: ClearRepresentative
    ) : DashboardRepresentative {
        override fun play() {
            clear.clear(DashboardRepresentative::class.java)
            navigation.update(Subscription)
        }

        override fun equals(other: Any?)=
            javaClass.name.equals(other?.javaClass?.name)

    }
}