package com.example.project_999

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface UiObservable<T : Any> : UiUpdate<T> {

    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())

    class Base<T : Any>() : UiObservable<T> {

        private var observer: UiObserver<T> = UiObserver.Empty()
        private var cachedData: T? = null
        override fun updateObserver(uiObserver: UiObserver<T>) {
            observer = uiObserver
            if (!observer.isEmpty()) {
                cachedData?.let {
                    observer.update(it)
//                    cachedData = null
                }
            }
        }

        override fun update(data: T) {
            if (observer.isEmpty()) cachedData = data
            else {
                cachedData = data
//                cachedData = null
                observer.update(data)
            }
        }

    }

    class Single<T : Any>() : UiObservable<T> {
        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()
        private var cachedData: T? = null

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(Single::class.java) {
            observer = uiObserver
            if (!observer.isEmpty()) {
                cachedData?.let {
                    observer.update(it)
                    cachedData = null
                }
            }
        }

        override fun update(data: T) = synchronized(Single::class.java) {
            if (observer.isEmpty()) cachedData = data
            else {
                cachedData = null
                observer.update(data)
            }
        }

    }
}

interface UiObserver<T : Any> : UiUpdate<T> {
    fun isEmpty(): Boolean = false

    class Empty<T : Any>() : UiObserver<T> {
        override fun isEmpty() = true
        override fun update(data: T) = Unit

    }
}

interface UiUpdate<T : Any> {

    fun update(data: T)
}