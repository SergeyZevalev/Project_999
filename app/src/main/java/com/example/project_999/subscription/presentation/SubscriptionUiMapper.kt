package com.example.project_999.subscription.presentation

import com.example.project_999.core.UiObservable
import com.example.project_999.subscription.domain.Mapper

class SubscriptionUiMapper(
    private val observable: UiObservable<SubscriptionUiState>
): Mapper {
    override fun map(canGoBackCallback: (Boolean) -> Unit) {
        observable.update(SubscriptionUiState.Finish)
        canGoBackCallback.invoke(true)
    }
}