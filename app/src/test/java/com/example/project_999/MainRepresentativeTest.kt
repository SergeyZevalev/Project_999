package com.example.project_999

import com.example.project_999.core.UiObserver
import com.example.project_999.dashboard.Dashboard
import com.example.project_999.main.MainRepresentative
import com.example.project_999.main.Navigation
import com.example.project_999.main.Screen
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainRepresentativeTest {

    private lateinit var representative: MainRepresentative
    private lateinit var navigation: FakeMutableNavigation
    private lateinit var callback: UiObserver<Screen>

    @Before
    fun setup(){
        navigation = FakeMutableNavigation.Base()
        callback = object : UiObserver<Screen> {
            override fun update(data: Screen) = Unit
        }
    }

    @Test
    fun main_scenario(){
        representative = MainRepresentative.Base(navigation)
        navigation.checkObserver(UiObserver.Empty())
        navigation.checkScreen(Screen.Empty)
        representative.startGettingUpdates(callback)
        navigation.checkObserver(callback)
        representative.showDashboard(true)
        navigation.checkScreen(Dashboard)
        representative.observed()
        navigation.checkClearCalled()
        representative.stopGettingUpdates()
        navigation.checkObserver(UiObserver.Empty())
    }
}

private interface FakeMutableNavigation : Navigation.Mutable{

    fun checkScreen(screen: Screen)
    fun checkClearCalled()
    fun checkObserver(observer: UiObserver<Screen>)
    class Base(): FakeMutableNavigation{

        private var updateCalledWithScreen: Screen = Screen.Empty
        private var clearCalled = false
        private var observer: UiObserver<Screen> = UiObserver.Empty()
        override fun checkScreen(screen: Screen) {
            assertEquals(screen, updateCalledWithScreen)
        }

        override fun checkClearCalled() {
            assertEquals(true, clearCalled)
        }

        override fun checkObserver(observer: UiObserver<Screen>) {
            assertEquals(observer, this.observer)
        }

        override fun clear() {
            clearCalled = true
        }

        override fun update(data: Screen) {
            updateCalledWithScreen = data
        }

        override fun updateObserver(uiObserver: UiObserver<Screen>) {
            observer = uiObserver
        }

    }
}