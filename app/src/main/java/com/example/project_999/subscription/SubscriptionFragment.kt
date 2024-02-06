package com.example.project_999.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.project_999.R
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.main.BaseFragment

class SubscriptionFragment: BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {

    override val clasz: Class<SubscriptionRepresentative> = SubscriptionRepresentative::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.subscription_button)
        button.setOnClickListener {
            representative.subscribe()
        }
    }
}