package com.example.project_999.core

import com.example.project_999.main.Screen

interface Representative<T : Any> {

    fun startGettingUpdates(callback: UiObserver<T>) = Unit
    fun stopGettingUpdates() = Unit

    /*
    private val handleDeath = HandleDeath.Base()
    fun activityCreated(bundleIsNull: Boolean) {
        if (bundleIsNull){
            handleDeath.firstOpening()
        } else {
            if (handleDeath.wasDeathHappened()) {
                handleDeath.deathHandled()
            } else {
            }
        }
    }
     */
}