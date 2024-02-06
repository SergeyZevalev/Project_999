package com.example.project_999.core

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.View.BaseSavedState

class CustomButton : androidx.appcompat.widget.AppCompatButton, ChangeVisible {

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
        super.onRestoreInstanceState(state)
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

class CustomVisibilityState : BaseSavedState {

    var visible = View.VISIBLE

    constructor(superState: Parcelable) : super(superState)
    private constructor(parcelIn: Parcel) : super(parcelIn) {
        visible = parcelIn.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visible)

    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomVisibilityState> {
        override fun createFromParcel(source: Parcel): CustomVisibilityState {
            return CustomVisibilityState(source)
        }

        override fun newArray(size: Int): Array<CustomVisibilityState?> {
            return arrayOfNulls(size)
        }

    }

}