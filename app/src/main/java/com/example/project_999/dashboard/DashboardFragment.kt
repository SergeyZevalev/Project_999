package com.example.project_999.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.project_999.R
import com.example.project_999.core.CustomButton
import com.example.project_999.core.CustomTextView
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.core.UiObserver
import com.example.project_999.main.BaseFragment

class DashboardFragment: BaseFragment<DashboardRepresentative>(R.layout.dashboard_layout) {

    override val clasz: Class<DashboardRepresentative> = DashboardRepresentative::class.java

    private lateinit var callback: UiObserver<PremiumDashboardUiState>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<CustomButton>(R.id.play_button)
        val textView = view.findViewById<CustomTextView>(R.id.show_playing_text_view)
        button.setOnClickListener {
            representative.play()
        }

        callback = object : UiObserver<PremiumDashboardUiState> {
            override fun update(data: PremiumDashboardUiState) {
                data.show(button, textView)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(callback)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }

}