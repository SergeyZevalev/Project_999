package com.example.project_999.dashboard

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Module
import com.example.project_999.core.Core
import com.example.project_999.main.UserPremiumCache

class DashBoardModule(
    private val core: Core,
    private val clear: ClearRepresentative
): Module<DashboardRepresentative> {
    override fun representative(): DashboardRepresentative {

        val isUserPremium = UserPremiumCache.Base(core.sharedPreferences()).isUserPremium()

        return if (isUserPremium) {
            DashboardRepresentative.Premium(PremiumDashBoardObservable.Base())
        } else {
            DashboardRepresentative.Base(core.navigation(), clear)
        }
    }
}