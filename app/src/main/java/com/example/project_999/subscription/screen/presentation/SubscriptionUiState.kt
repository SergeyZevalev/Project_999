package com.example.project_999.subscription.screen.presentation

import com.example.project_999.core.ChangeVisible
import com.example.project_999.subscription.progress.presentation.Subscribe
import java.io.Serializable

interface SubscriptionUiState : Serializable {

    fun observed(representative: SubscriptionObserved) = representative.observed()

    fun restoreAfterDeath(
        observable: SubscriptionObservable
    ) = observable.update(this)

    fun show(
        subscribeButton: ChangeVisible,
        progressBar: Subscribe,
        finishButton: ChangeVisible
    )

    object Initial : SubscriptionUiState {
        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: Subscribe,
            finishButton: ChangeVisible
        ) {
            subscribeButton.show()
            finishButton.hide()
        }
    }

    object Progress : SubscriptionUiState {

        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: Subscribe,
            finishButton: ChangeVisible
        ) {
            subscribeButton.hide()
            progressBar.subscribe()
            finishButton.hide()
        }

    }

    object Finish : SubscriptionUiState {

        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: Subscribe,
            finishButton: ChangeVisible
        ) {
            subscribeButton.hide()
            finishButton.show()
        }

    }

    object Empty : SubscriptionUiState {
        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: Subscribe,
            finishButton: ChangeVisible
        ) = Unit

        override fun observed(representative: SubscriptionObserved) = Unit

        override fun restoreAfterDeath(observable: SubscriptionObservable) = Unit
    }
}