package com.example.project_999.core

import androidx.annotation.MainThread

interface UiObservable<T : Any> : UiUpdate<T>, UpdateObserver<T> {


    abstract class Single<T : Any>() : UiObservable<T> {
        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()
        @Volatile
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
            if (observer.isEmpty())
                cachedData = data
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

interface UpdateObserver<T: Any> {
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())
}

interface UiUpdate<T : Any> {

    fun update(data: T)
}