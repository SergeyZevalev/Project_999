package com.example.project_999.subscription.presentation

import com.example.project_999.core.ChangeVisible
import java.io.Serializable

interface SubscriptionUiState : Serializable {

    fun observed(representative: SubscriptionObserved) = representative.observed()

    fun restoreAfterDeath(
        representative: SubscriptionInner,
        observable: SubscriptionObservable
    ) = observable.update(this)

    fun show(
        subscribeButton: ChangeVisible,
        progressBar: ChangeVisible,
        finishButton: ChangeVisible
    )

    object Initial : SubscriptionUiState {
        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: ChangeVisible,
            finishButton: ChangeVisible
        ) {
            subscribeButton.show()
            progressBar.hide()
            finishButton.hide()
        }
    }

    object Progress : SubscriptionUiState {

        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: ChangeVisible,
            finishButton: ChangeVisible
        ) {
            subscribeButton.hide()
            progressBar.show()
            finishButton.hide()
        }

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: SubscriptionObservable
        ) {
            representative.subscribeInner()
        }

        override fun observed(representative: SubscriptionObserved) = Unit
    }

    object Finish : SubscriptionUiState {

        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: ChangeVisible,
            finishButton: ChangeVisible
        ) {
            subscribeButton.hide()
            progressBar.hide()
            finishButton.show()
        }

        override fun observed(representative: SubscriptionObserved) = Unit
    }

    object Empty : SubscriptionUiState {
        override fun show(
            subscribeButton: ChangeVisible,
            progressBar: ChangeVisible,
            finishButton: ChangeVisible
        ) = Unit

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: SubscriptionObservable
        ) = Unit
    }
}