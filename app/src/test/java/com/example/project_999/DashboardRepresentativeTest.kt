package com.example.project_999

import com.example.project_999.core.Module
import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.DashboardRepresentative
import com.example.project_999.dashboard.PremiumDashBoardObservable
import com.example.project_999.dashboard.PremiumDashboardUiState
import com.example.project_999.main.UserPremiumCache
import com.example.project_999.subscription.screen.presentation.Subscription
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DashboardRepresentativeTest {

    private lateinit var observable: FakeDashboardObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear
    private lateinit var representative: DashboardRepresentative
    private lateinit var userPremiumCache: FakeUserPremiumCache
    private lateinit var observer: UiObserver<PremiumDashboardUiState>
    private lateinit var module: FakeModule

    @Before
    fun setup(){
        navigation = FakeNavigation.Base()
        clear = FakeClear.Base()
        observable = FakeDashboardObservable.Base()
        observer = object : UiObserver<PremiumDashboardUiState>{
            override fun update(data: PremiumDashboardUiState) = Unit
        }

    }

    @Test
    fun main_scenario(){
        userPremiumCache = FakeUserPremiumCache.Base()
        module = FakeModule.Base(observable, clear, navigation, userPremiumCache)
        representative = module.representative()
        module.checkRepresentative(DashboardRepresentative.Base(navigation, clear))
        observable.checkObserver(UiObserver.Empty())
        observable.checkState(PremiumDashboardUiState.Empty)
        representative.play()
        clear.checkClearCalledWith(DashboardRepresentative::class.java)
        navigation.checkScreen(Subscription)
        userPremiumCache.saveUserPremium()
        representative = module.representative()
        module.checkRepresentative(DashboardRepresentative.Premium(observable))
        representative.startGettingUpdates(observer)
        observable.checkState(PremiumDashboardUiState.Empty)
        observable.checkObserver(observer)
        representative.play()
        representative.observed()
        observable.checkClearCalled()
        observable.checkState(PremiumDashboardUiState.Playing)
    }
}

private interface FakeModule: Module<DashboardRepresentative>{

    fun checkRepresentative(representative: DashboardRepresentative)
    class Base(
        private val observable: FakeDashboardObservable,
        private val clear: FakeClear,
        private val navigation: FakeNavigation,
        private val userPremiumCache: FakeUserPremiumCache
    ) : FakeModule {
        override fun checkRepresentative(representative: DashboardRepresentative) {
            assertEquals(representative, representative())
        }
        override fun representative(): DashboardRepresentative {
            val isUserPremium = userPremiumCache.isUserPremium()

            return if (isUserPremium) DashboardRepresentative.Premium(observable)
            else DashboardRepresentative.Base(navigation, clear)
        }

    }
}
private interface FakeUserPremiumCache: UserPremiumCache.Mutable {

    class Base() : FakeUserPremiumCache {
        private var premiumUser = false
        override fun saveUserPremium() {
            premiumUser = true
        }
        override fun isUserPremium() = premiumUser

    }
}

private interface FakeDashboardObservable : PremiumDashBoardObservable {

    fun checkClearCalled()
    fun checkState(state: PremiumDashboardUiState)

    fun checkObserver(observer: UiObserver<PremiumDashboardUiState>)

    class Base() : FakeDashboardObservable {

        private var clearCalled = false
        private var state: PremiumDashboardUiState = PremiumDashboardUiState.Empty
        private var observer: UiObserver<PremiumDashboardUiState> = UiObserver.Empty()
        override fun checkClearCalled() {
            assertEquals(true, clearCalled)
        }

        override fun checkState(state: PremiumDashboardUiState) {
            assertEquals(state, this.state)
        }

        override fun checkObserver(observer: UiObserver<PremiumDashboardUiState>) {
            assertEquals(observer, this.observer)
        }

        override fun clear() {
            clearCalled = true
        }

        override fun update(data: PremiumDashboardUiState) {
            state = data
        }

        override fun updateObserver(uiObserver: UiObserver<PremiumDashboardUiState>) {
            observer = uiObserver
        }


    }
}