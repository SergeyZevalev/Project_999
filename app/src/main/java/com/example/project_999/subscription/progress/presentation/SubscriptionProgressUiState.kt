package com.example.project_999.subscription.progress.presentation

import com.example.project_999.core.ChangeVisible
import com.example.project_999.core.UiUpdate
import com.example.project_999.subscription.screen.presentation.SubscriptionInner
import java.io.Serializable

interface SubscriptionProgressUiState : Serializable {

    fun show(hideAndShow: ChangeVisible)
    fun restoreAfterDeath(
        subscribeInner: SubscriptionInner,
        observable: UiUpdate<SubscriptionProgressUiState>
    )
    fun observed(representative: SubscriptionProgressRepresentative) = Unit

    object Show : SubscriptionProgressUiState {

        override fun show(hideAndShow: ChangeVisible) = hideAndShow.show()

        override fun restoreAfterDeath(
            subscribeInner: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) = subscribeInner.subscribeInner()

    }

    object Hide : SubscriptionProgressUiState {

        override fun show(hideAndShow: ChangeVisible) = hideAndShow.hide()

        override fun restoreAfterDeath(
            subscribeInner: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) = observable.update(this)

        override fun observed(representative: SubscriptionProgressRepresentative) =
            representative.observed()

    }

    object Empty : SubscriptionProgressUiState {
        override fun show(hideAndShow: ChangeVisible)  = Unit
        override fun restoreAfterDeath(
            subscribeInner: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) = Unit
    }
}