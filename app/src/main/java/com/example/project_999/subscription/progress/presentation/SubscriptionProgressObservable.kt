package com.example.project_999.subscription.progress.presentation

import com.example.project_999.core.UiObservable

interface SubscriptionProgressObservable : UiObservable<SubscriptionProgressUiState> {

    fun save(saveSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Save)

    class Base : UiObservable.Base<SubscriptionProgressUiState>(SubscriptionProgressUiState.Empty),
        SubscriptionProgressObservable {

        override fun save(saveSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Save) {
            saveSubscriptionProgressState.save(cachedData)
        }
    }
}