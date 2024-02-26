package com.example.project_999.main

import com.example.project_999.core.UiObservable
import com.example.project_999.core.UiUpdate
import com.example.project_999.core.UpdateObserver

interface Navigation {

    interface Update : UiUpdate<Screen>

    interface Observe : UpdateObserver<Screen>

    interface Mutable : Update, Observe

    class Base : UiObservable.Single<Screen>(Screen.Empty), Mutable {

    }
}