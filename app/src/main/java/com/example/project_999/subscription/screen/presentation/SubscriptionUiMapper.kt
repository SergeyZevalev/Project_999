package com.example.project_999.subscription.screen.presentation

import com.example.project_999.core.UiUpdate
import com.example.project_999.subscription.progress.domain.Mapper
import com.example.project_999.subscription.progress.presentation.SubscriptionProgressUiState

class SubscriptionUiMapper(
    private val observable: UiUpdate<SubscriptionUiState>,
    private val progressObservable: UiUpdate<SubscriptionProgressUiState>
): Mapper {

    override fun map() {
        observable.update(SubscriptionUiState.Finish)
        progressObservable.update(SubscriptionProgressUiState.Hide)
    }
}