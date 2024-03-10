package com.example.project_999.subscription.presentation

import android.os.Bundle
import com.example.project_999.core.SaveAndRestore

interface SubscriptionUiSaveAndRestoreState {

    interface Save : SaveAndRestore.Save<SubscriptionUiState>

    interface Read: SaveAndRestore.Read<SubscriptionUiState>

    interface Mutable: Save, Read

    class Base(
        private val bundle: Bundle?
    ) : Mutable, SaveAndRestore.Abstract<SubscriptionUiState>(
        bundle,
        "ket",
        SubscriptionUiState::class.java
    )

}