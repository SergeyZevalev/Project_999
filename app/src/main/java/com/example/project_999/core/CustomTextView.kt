package com.example.project_999.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

class CustomTextView : androidx.appcompat.widget.AppCompatTextView, ChangeVisible {


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

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val customTextViewState = CustomVisibilityState(it)
            customTextViewState.save(this)
            return customTextViewState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val customTextViewState = state as CustomVisibilityState?
        super.onRestoreInstanceState(state?.superState)
        customTextViewState?.let {
            it.restore(this)
        }
    }

    override fun show() {
        visibility = View.VISIBLE
    }

    override fun hide() {
        visibility = View.GONE
    }

}
