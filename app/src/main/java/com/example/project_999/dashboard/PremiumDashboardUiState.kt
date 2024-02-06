package com.example.project_999.dashboard

import com.example.project_999.core.ChangeVisible

interface PremiumDashboardUiState {

    fun show(button: ChangeVisible, textView: ChangeVisible)
    object Playing : PremiumDashboardUiState {
        override fun show(button: ChangeVisible, textView: ChangeVisible) {
            button.hide()
            textView.show()
        }


    }
}