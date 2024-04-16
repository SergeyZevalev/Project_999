package com.example.project_999.subscription.screen.presentation

import androidx.annotation.MainThread
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.Dashboard
import com.example.project_999.main.Navigation
import com.example.project_999.subscription.progress.presentation.ComeBack
import com.example.project_999.subscription.progress.presentation.Subscribe

interface SubscriptionRepresentative : Representative<SubscriptionUiState>, SaveSubscriptionUIState,
    SubscriptionObserved, ComeBack<Boolean>, Subscribe  {

    fun initState(initState: SubscriptionUiSaveAndRestoreState.Read)
    fun finish()

    class Base(
        private val handleDeath: HandleDeath,
        private val observable: SubscriptionObservable,
        private val clear: ()-> Unit,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {

        override fun initState(initState: SubscriptionUiSaveAndRestoreState.Read) {
            if (initState.isEmpty()) {
                handleDeath.firstOpening()
                observable.update(SubscriptionUiState.Initial)
            } else {
                if (handleDeath.wasDeathHappened()) {
                    handleDeath.deathHandled()
                    initState.read().restoreAfterDeath(observable)
                }
            }
        }

        override fun saveState(saveState: SubscriptionUiSaveAndRestoreState.Save) =
            observable.saveState(saveState)

        override fun subscribe() =
            observable.update(SubscriptionUiState.Progress)

        override fun finish() {
            navigation.update(Dashboard)
            clear.invoke()
        }

        override fun comeback(data: Boolean) {
            if (data) finish()
        }

        override fun observed() = observable.clear()

        override fun startGettingUpdates(callback: UiObserver<SubscriptionUiState>) =
            observable.updateObserver(callback)

        override fun stopGettingUpdates() =  observable.updateObserver()

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

