package com.example.project_999.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_999.R
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.core.Representative
import com.example.project_999.core.UiObserver

class MainActivity : AppCompatActivity(), ProvideRepresentative {

    private lateinit var representative: MainRepresentative
    private lateinit var uiObserver: UiObserver<Screen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        uiObserver = object : UiObserver<Screen> {
            override fun update(data: Screen) = runOnUiThread {
                data.show(supportFragmentManager, R.id.container)
                data.observed(representative)
            }
        }

        representative =  provideRepresentative(MainRepresentative::class.java)

        representative.showDashboard(savedInstanceState == null)

    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(uiObserver)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }

    override fun <T : Representative<*>> provideRepresentative(clasz: Class<T>) =
        (application as ProvideRepresentative).provideRepresentative(clasz)


}


