package com.example.project_999.subscription.progress.presentation

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SubscriptionProgressSaveState : View.BaseSavedState, SaveAndRestoreSubscriptionProgressState.Mutable {

    private var visible = View.VISIBLE
    private var state : SubscriptionProgressUiState = SubscriptionProgressUiState.Empty

    fun save(view: View) {
        visible = view.visibility
    }

    fun restore(view: View) {
        view.visibility = visible
    }

    constructor(superState: Parcelable) : super(superState)
    private constructor(parcelIn: Parcel) : super(parcelIn) {
        visible = parcelIn.readInt()
        state = parcelIn.readSerializable() as SubscriptionProgressUiState
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visible)
        out.writeSerializable(state)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SubscriptionProgressSaveState> {
        override fun createFromParcel(source: Parcel): SubscriptionProgressSaveState {
            return SubscriptionProgressSaveState(source)
        }

        override fun newArray(size: Int): Array<SubscriptionProgressSaveState?> {
            return arrayOfNulls(size)
        }

    }

    override fun save(state: SubscriptionProgressUiState) {
        this.state = state
    }

    override fun restore(): SubscriptionProgressUiState {
        return state
    }

}