package com.example.project_999.subscription.presentation

import androidx.annotation.MainThread
import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.Dashboard
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.main.Navigation
import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.domain.SubscriptionInteractor

interface SubscriptionRepresentative : Representative<SubscriptionUiState>, SaveSubscriptionUIState,
    SubscriptionObserved, SubscriptionInner {

    fun initState(initState: SubscriptionUiSaveAndRestoreState.Read)

    @MainThread
    fun subscribe()
    fun finish()
    class Base(
        private val handleDeath: HandleDeath,
        private val observable: SubscriptionObservable,
        private val clear: ClearRepresentative,
        private val interactor: SubscriptionInteractor,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {
        override fun initState(initState: SubscriptionUiSaveAndRestoreState.Read) {
            if (initState.isEmpty()) {
                handleDeath.firstOpening()
                observable.update(SubscriptionUiState.Initial)
            } else {
                if (handleDeath.wasDeathHappened()) {
                    handleDeath.deathHandled()
                    initState.read().restoreAfterDeath(this, observable)
                }
            }
        }

        override fun saveState(saveState: SubscriptionUiSaveAndRestoreState.Save) {
            observable.saveState(saveState)
        }

        override fun subscribe() {
            subscribeInner()
        }

        private val callback: () -> Unit = { observable.update(SubscriptionUiState.Finish) }
        override fun subscribeInner() {
            observable.update(SubscriptionUiState.Progress)
            interactor.subscribe(callback)
        }

        override fun finish() {
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(Dashboard)
        }

        override fun observed() = observable.clear()

        override fun startGettingUpdates(callback: UiObserver<SubscriptionUiState>) {
            observable.updateObserver(callback)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
        }

    }
}

interface SaveSubscriptionUIState {

    fun saveState(saveState: SubscriptionUiSaveAndRestoreState.Save)

}

interface SubscriptionObserved {
    fun observed()
}

interface SubscriptionInner {

    fun subscribeInner()
}