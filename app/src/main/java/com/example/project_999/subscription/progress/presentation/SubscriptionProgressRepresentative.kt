package com.example.project_999.subscription.progress.presentation

import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Representative
import com.example.project_999.core.RunAsync
import com.example.project_999.core.UiObserver
import com.example.project_999.subscription.progress.domain.SubscriptionInteractor
import com.example.project_999.subscription.progress.domain.SubscriptionResult
import com.example.project_999.subscription.screen.presentation.SubscriptionInner
import com.example.project_999.subscription.screen.presentation.SubscriptionObserved
import com.example.project_999.subscription.screen.presentation.SubscriptionUiMapper

interface SubscriptionProgressRepresentative : Representative<SubscriptionProgressUiState>,
    SubscriptionInner, ComeBack<ComeBack<Boolean>>, Init, SubscriptionObserved, Subscribe {

    suspend fun subscribeInternal()
    fun save(saveSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Save)
    fun restore(restoreSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Restore)

    class Base(
        private val observable: SubscriptionProgressObservable,
        private val handleDeath: HandleDeath,
        private val runAsync: RunAsync,
        private val mapper: SubscriptionUiMapper,
        private val interactor: SubscriptionInteractor
    ) : Representative.Abstract<SubscriptionProgressUiState>(runAsync),
        SubscriptionProgressRepresentative {

        private val uiBlock: (SubscriptionResult) -> Unit = {
            it.map(mapper)
        }

        override suspend fun subscribeInternal() = handleAsyncInternal({
            interactor.subscribeInternal()
        }, uiBlock)

        override fun restore(restoreSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Restore) {
            if (handleDeath.wasDeathHappened()) {
                handleDeath.deathHandled()
                val uiState = restoreSubscriptionProgressState.restore()
                uiState.restoreAfterDeath(this, observable)
            }
        }

        override fun save(saveSubscriptionProgressState: SaveAndRestoreSubscriptionProgressState.Save) =
            observable.save(saveSubscriptionProgressState)

        override fun subscribeInner() = handleAsync( {interactor.subscribe()} , uiBlock)

        override fun comeback(data: ComeBack<Boolean>) = data.comeback(observable.canGoBack())
        override fun init(firstRun: Boolean) {
            if (firstRun) {
                handleDeath.firstOpening()
                observable.update(SubscriptionProgressUiState.Hide)
            }
        }

        override fun observed() = observable.clear()
        override fun subscribe() {
            observable.update(SubscriptionProgressUiState.Show)
            subscribeInner()
        }

        override fun startGettingUpdates(callback: UiObserver<SubscriptionProgressUiState>) =
            observable.updateObserver(callback)

        override fun stopGettingUpdates() = observable.updateObserver()

    }
}
