package com.example.project_999.dashboard

import com.example.project_999.core.UiObservable

interface PremiumDashBoardObservable : UiObservable<PremiumDashboardUiState> {

    class Base : UiObservable.Base<PremiumDashboardUiState>(
        PremiumDashboardUiState.Empty
    ), PremiumDashBoardObservable
}