package com.example.project_999.subscription

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.project_999.R
import com.example.project_999.core.CustomButton
import com.example.project_999.core.CustomProgressBar
import com.example.project_999.core.UiObserver
import com.example.project_999.main.BaseFragment

class SubscriptionFragment: BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {

    override val clasz: Class<SubscriptionRepresentative> = SubscriptionRepresentative::class.java

    private lateinit var observer: UiObserver<SubscriptionUiState>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscribeButton = view.findViewById<CustomButton>(R.id.subscription_button)
        val progressBar = view.findViewById<CustomProgressBar>(R.id.progress_bar)
        val finishButton = view.findViewById<CustomButton>(R.id.finish_button)

        subscribeButton.setOnClickListener {
            representative.subscribe()
        }
        finishButton.setOnClickListener {
            representative.finish()
        }

        observer = object : UiObserver<SubscriptionUiState>{
            override fun update(data: SubscriptionUiState) = requireActivity().runOnUiThread {
                data.observed(representative)
                data.show(subscribeButton, progressBar, finishButton)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("prj999", "SubscriptionFragmentOnSaveInstanceState")
        representative.saveState(SubscriptionUiSaveAndRestoreState.Base(outState))
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            representative.initState(SubscriptionUiSaveAndRestoreState.Base(savedInstanceState))
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(observer)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }
}