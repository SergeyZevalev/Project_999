package com.example.project_999.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar


class CustomProgressBar : ProgressBar, ChangeVisible {

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

    override fun onSaveInstanceState(): Parcelable? =
        super.onSaveInstanceState()?.let {
            val customButtonState = CustomVisibilityState(it)
            customButtonState.visible = visibility
            return customButtonState

        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val customButtonState = state as CustomVisibilityState?
        super.onRestoreInstanceState(state?.superState)
        customButtonState?.let {
            visibility = it.visible
        }
    }

    override fun show() {
        visibility = View.VISIBLE
    }

    override fun hide() {
        visibility = View.GONE
    }

}
