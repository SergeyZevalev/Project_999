package com.example.project_999.subscription.progress.presentation

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import com.example.project_999.core.ChangeVisible
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.core.UiObserver


class SubscriptionProgressBar : ProgressBar, SubscriptionProgressActions {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(context: Context) : super(
        context
    )

    private val representative: SubscriptionProgressRepresentative by lazy {
        (context.applicationContext as ProvideRepresentative).provideRepresentative(
            SubscriptionProgressRepresentative::class.java
        )
    }

    private val observer: UiObserver<SubscriptionProgressUiState> = object : UiObserver<SubscriptionProgressUiState> {
        override fun update(data: SubscriptionProgressUiState) = with(data){
            show(this@SubscriptionProgressBar)
            observed(representative)
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
        super.onSaveInstanceState()?.let {
            val state = SubscriptionProgressSaveState(it)
            representative.save(state)
            state.save(this)
            return state
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as SubscriptionProgressSaveState?
        representative.restore(restoredState!!)
        super.onRestoreInstanceState(state?.superState)
        restoredState.let {
            it.restore(this)
        }

    }

    override fun init(firstRun: Boolean) = representative.init(firstRun)

    override fun resume() = representative.startGettingUpdates(observer)

    override fun pause() = representative.stopGettingUpdates()

    override fun subscribe() = representative.subscribe()

    override fun show() {
        visibility = View.VISIBLE
    }

    override fun hide() {
        visibility = View.GONE
    }

    override fun comeback(data: ComeBack<Boolean>) =  representative.comeback(data)

}

interface ComeBack<T> {

    fun comeback(data: T)
}

interface SubscriptionProgressActions : Subscribe, ChangeVisible, ComeBack<ComeBack<Boolean>>, Init {

    fun resume()
    fun pause()
}

interface Init {

    fun init(firstRun: Boolean)
}

interface Subscribe{

    fun subscribe()
}

interface SaveAndRestoreSubscriptionProgressState {

    interface Save {
        fun save(state: SubscriptionProgressUiState)
    }

    interface Restore {
        fun restore() : SubscriptionProgressUiState
    }

    interface Mutable : Save, Restore
}
