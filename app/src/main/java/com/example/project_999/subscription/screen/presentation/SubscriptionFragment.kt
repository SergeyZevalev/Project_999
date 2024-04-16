package com.example.project_999.subscription.screen.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.project_999.R
import com.example.project_999.core.CustomButton
import com.example.project_999.subscription.progress.presentation.SubscriptionProgressBar
import com.example.project_999.core.UiObserver
import com.example.project_999.main.BaseFragment

class SubscriptionFragment :
    BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {

    override val clasz: Class<SubscriptionRepresentative> = SubscriptionRepresentative::class.java
    private var progressBar: SubscriptionProgressBar? = null

    private lateinit var observer: UiObserver<SubscriptionUiState>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscribeButton = view.findViewById<CustomButton>(R.id.subscription_button)

        progressBar = view.findViewById(R.id.progress_bar)

        val finishButton = view.findViewById<CustomButton>(R.id.finish_button)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                progressBar!!.comeback(representative)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(callback)

        subscribeButton.setOnClickListener {

            representative.subscribe()
        }
        finishButton.setOnClickListener {
            representative.finish()
        }

        observer = object : UiObserver<SubscriptionUiState> {
            override fun update(data: SubscriptionUiState) {
                data.observed(representative)
                data.show(subscribeButton, progressBar!!, finishButton)
            }
        }

        val initState = SubscriptionUiSaveAndRestoreState.Base(savedInstanceState)
        representative.initState(initState)
        progressBar!!.init(initState.isEmpty())


    }

    override fun onSaveInstanceState(outState: Bundle) {
        representative.saveState(SubscriptionUiSaveAndRestoreState.Base(outState))
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(observer)
        progressBar!!.resume()
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
        progressBar!!.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressBar = null
    }
}