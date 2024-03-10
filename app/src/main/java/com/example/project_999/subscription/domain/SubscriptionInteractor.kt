package com.example.project_999.subscription.domain

import com.example.project_999.main.UserPremiumCache

interface SubscriptionInteractor {

    fun subscribe(callback: ()-> Unit)
    class Base(
        private val userPremiumCache: UserPremiumCache.Save
    ) : SubscriptionInteractor {


        override fun subscribe(callback: () -> Unit) {
            Thread {
                Thread.sleep(5000)
                userPremiumCache.saveUserPremium()
                callback.invoke()
            }.start()
        }
    }
}