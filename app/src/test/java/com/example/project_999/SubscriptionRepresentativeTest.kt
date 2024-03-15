package com.example.project_999

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.HandleDeath
import com.example.project_999.core.Representative
import com.example.project_999.core.RunAsync
import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.Dashboard
import com.example.project_999.main.Navigation
import com.example.project_999.main.Screen
import com.example.project_999.subscription.domain.SubscriptionInteractor
import com.example.project_999.subscription.presentation.SubscriptionObservable
import com.example.project_999.subscription.presentation.SubscriptionRepresentative
import com.example.project_999.subscription.presentation.SubscriptionUiSaveAndRestoreState
import com.example.project_999.subscription.presentation.SubscriptionUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class SubscriptionRepresentativeTest {

    private lateinit var representative: SubscriptionRepresentative
    private lateinit var observable: FakeSubscriptionObservable
    private lateinit var clear: FakeClear
    private lateinit var interactor: FakeInteractor
    private lateinit var navigation: FakeNavigation
    private lateinit var handleDeath: FakeHandleDeath
    private lateinit var observer: UiObserver<SubscriptionUiState>
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        observable = FakeSubscriptionObservable.Base()
        clear = FakeClear.Base()
        interactor = FakeInteractor.Base()
        navigation = FakeNavigation.Base()
        handleDeath = FakeHandleDeath.Base()
        observer = object : UiObserver<SubscriptionUiState>{
            override fun update(data: SubscriptionUiState) = Unit
        }
        runAsync = FakeRunAsync.Base()
        representative = SubscriptionRepresentative.Base(
            runAsync,
            handleDeath,
            observable,
            clear,
            interactor,
            navigation
        )
    }

    @Test
    fun main_scenario_test() {

        val saveAndRestoreState = FakeSaveAndRestoreState.Base()
        observable.checkState(SubscriptionUiState.Empty)
        observable.update(SubscriptionUiState.Initial)
        observable.checkState(SubscriptionUiState.Initial)
        representative.initState(saveAndRestoreState)
        assertEquals(false, handleDeath.wasDeathHappened())
        observable.checkState(SubscriptionUiState.Initial)
        observable.checkObserver(UiObserver.Empty())
        representative.startGettingUpdates(observer)
        observable.checkObserver(observer)

        representative.subscribe()
        observable.checkState(SubscriptionUiState.Progress)
        runAsync.pingResult()
        observable.checkState(SubscriptionUiState.Finish)
        representative.finish()
        clear.checkClearCalledWith(SubscriptionRepresentative::class.java)
        representative.observed()
        observable.checkClearCalled()
        representative.stopGettingUpdates()
        observable.checkObserver(UiObserver.Empty())
        navigation.checkScreen(Dashboard)

    }
    @Test
    fun activity_death_within_progress_test() {
        val saveAndRestoreState = FakeSaveAndRestoreState.Base()
        representative.initState(saveAndRestoreState)
        representative.startGettingUpdates(observer)
        representative.subscribe()
        representative.stopGettingUpdates()
        representative.saveState(saveAndRestoreState)
        observable.checkObserver(UiObserver.Empty())
        observable.checkState(SubscriptionUiState.Progress)

        representative.initState(saveAndRestoreState)
        representative.startGettingUpdates(observer)
        handleDeath.checkFirstOpening(1)
        observable.checkState(SubscriptionUiState.Progress)
        observable.checkObserver(observer)
    }
    @Test
    fun process_death_within_progress_test(){
        val saveAndRestoreState = FakeSaveAndRestoreState.Base()
        representative.initState(saveAndRestoreState)
        handleDeath.checkFirstOpening(1)
        representative.startGettingUpdates(observer)
        representative.subscribe()
        representative.stopGettingUpdates()
        representative.saveState(saveAndRestoreState)

        setup()

        assertEquals(true, handleDeath.wasDeathHappened())
        assertEquals(SubscriptionUiState.Progress, saveAndRestoreState.read())
        observable.checkObserver(UiObserver.Empty())
        representative.initState(saveAndRestoreState)
        assertEquals(SubscriptionUiState.Progress, saveAndRestoreState.read())
        observable.checkState(SubscriptionUiState.Empty)
        observable.checkObserver(UiObserver.Empty())
        assertEquals(false, handleDeath.wasDeathHappened())
        handleDeath.checkFirstOpening(0)
        representative.startGettingUpdates(observer)
        observable.checkObserver(observer)
        runAsync.pingResult()
        observable.checkState(SubscriptionUiState.Finish)
    }

    @Test
    fun can_go_back(){

    }
}

internal interface FakeHandleDeath : HandleDeath {

    fun checkFirstOpening(count: Int)
    class Base() : FakeHandleDeath {

        private var deathHappened = true
        private var firstOpeningCount = 0
        override fun checkFirstOpening(count: Int) {
            assertEquals(count, firstOpeningCount)
        }

        override fun firstOpening() {
            deathHappened = false
            firstOpeningCount++
        }
        override fun wasDeathHappened() = deathHappened
        override fun deathHandled() {
            deathHappened = false
        }

    }
}

private interface FakeSubscriptionObservable : SubscriptionObservable {

    fun checkClearCalled()
    fun checkState(state: SubscriptionUiState)

    fun checkObserver(observer: UiObserver<SubscriptionUiState>)
    class Base() : FakeSubscriptionObservable {

        private var clearCalled = false
        private var state: SubscriptionUiState = SubscriptionUiState.Empty
        private var observer: UiObserver<SubscriptionUiState> = UiObserver.Empty()

        override fun checkClearCalled() {
            assertEquals(true, clearCalled)
        }

        override fun checkState(state: SubscriptionUiState) {
            assertEquals(state, this.state)
        }

        override fun checkObserver(observer: UiObserver<SubscriptionUiState>) {
            assertEquals(observer, this.observer)
        }

        override fun clear() {
            state = SubscriptionUiState.Empty
            clearCalled = true
        }

        override fun update(data: SubscriptionUiState) {
            state = data
        }

        override fun updateObserver(uiObserver: UiObserver<SubscriptionUiState>) {
            observer = uiObserver
        }

        override fun saveState(saveState: SubscriptionUiSaveAndRestoreState.Save) {
            saveState.save(state)
        }

    }
}

internal interface FakeSaveAndRestoreState : SubscriptionUiSaveAndRestoreState.Mutable {

    class Base() : FakeSaveAndRestoreState {

        private var state: SubscriptionUiState = SubscriptionUiState.Empty
        override fun save(data: SubscriptionUiState) {
            state = data
        }

        override fun read() = state

        override fun isEmpty() = state == SubscriptionUiState.Empty


    }
}

internal interface FakeClear : ClearRepresentative {

    fun checkClearCalledWith(clasz: Class<out Representative<*>>)
    class Base() : FakeClear {

        private var clearCalledClass: Class<out Representative<*>>? = null
        override fun checkClearCalledWith(clasz: Class<out Representative<*>>) {
            assertEquals(clasz, clearCalledClass)
        }

        override fun clear(clazz: Class<out Representative<*>>) {
            clearCalledClass = clazz
        }


    }
}

internal interface FakeInteractor : SubscriptionInteractor {

    fun checkSubscribeCalledCount(count: Int)
    class Base() : FakeInteractor {

        private var subscribeCalledCount = 0
        override fun checkSubscribeCalledCount(count: Int) {
            assertEquals(count, subscribeCalledCount)
        }

        override suspend fun subscribe() {
            subscribeCalledCount++
        }

    }
}

private interface FakeRunAsync: RunAsync{

    fun checkClearCalled(times: Int)
    fun pingResult()
    class Base(): FakeRunAsync{

        private var cachedBlock: (Any) -> Unit = {}
        private var cached: Any = Unit
        private var clearCalled = 0
        override fun checkClearCalled(times: Int) {
            assertEquals(times, clearCalled)
        }

        override fun pingResult() {
            cachedBlock.invoke(cached)
        }

        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) = runBlocking {
                cached = backgroundBlock.invoke()
                cachedBlock = uiBlock as (Any) -> Unit
            }


        override fun clear() {
            clearCalled++
        }

    }
}
internal interface FakeNavigation : Navigation.Update {

    fun checkScreen(screen: Screen)
    class Base() : FakeNavigation {

        private var updateCalledWithScreen: Screen = Screen.Empty
        override fun checkScreen(screen: Screen) {
            assertEquals(screen, updateCalledWithScreen)
        }

        override fun update(data: Screen) {
            updateCalledWithScreen = data
        }


    }
}
