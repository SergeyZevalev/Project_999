package com.example.project_999.subscription

import com.example.project_999.core.UiObservable

interface SubscriptionObservable: UiObservable<SubscriptionUiState>, SaveSubscriptionUIState {

    class Base: UiObservable.Single<SubscriptionUiState>(SubscriptionUiState.Empty), SubscriptionObservable {
        override fun saveState(saveState: SubscriptionUiSaveAndRestoreState.Save) {
            saveState.save(cachedData)
        }
    }
}