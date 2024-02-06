package com.example.project_999.dashboard

import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver
import com.example.project_999.main.Navigation
import com.example.project_999.subscription.Subscription

interface DashboardRepresentative : Representative<PremiumDashboardUiState> {

    fun play()

    class Premium(
        private val observable: PremiumDashBoardObservable
    ) : DashboardRepresentative {
        override fun play() {
            observable.update(PremiumDashboardUiState.Playing)
        }

        override fun startGettingUpdates(callback: UiObserver<PremiumDashboardUiState>) {
            observable.updateObserver(callback)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
        }
    }

    class Base(
        private val navigation: Navigation.Update
    ) : DashboardRepresentative {
        override fun play() = navigation.update(Subscription)



    }
}