package com.example.project_999

import android.app.Application
import android.os.Process
import android.util.Log

class MyApp : Application() {

    lateinit var mainRepresentative: MainRepresentative
    private val handleDeath = HandleDeath.Base()
    override fun onCreate() {
        super.onCreate()
        mainRepresentative = MainRepresentative.Base(UiObservable.Single())
    }
//    fun activityCreated(bundleIsNull: Boolean) {
//        if (bundleIsNull){
//            handleDeath.firstOpening()
//        } else {
//            if (handleDeath.wasDeathHappened()) {
//                handleDeath.deathHandled()
//            } else {
//            }
//        }
//    }

}