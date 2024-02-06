package com.example.project_999.subscription

import com.example.project_999.core.ClearRepresentative
import com.example.project_999.core.Core
import com.example.project_999.core.Module
import com.example.project_999.main.UserPremiumCache

class SubscriptionModule(
    private val core: Core,
    private val clear: ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative() = SubscriptionRepresentative.Base(
        clear,
        UserPremiumCache.Base(core.sharedPreferences()),
            core.navigation())

}