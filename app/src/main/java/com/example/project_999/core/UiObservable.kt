package com.example.project_999.core

import androidx.annotation.MainThread

interface UiObservable<T : Any> : UiUpdate<T>, UpdateObserver<T> {
    fun clear()
    @MainThread
    abstract class Base<T : Any>(
        private val empty: T
    ) : UiObservable<T> {

        private var observer: UiObserver<T> = UiObserver.Empty()

        protected var cachedData: T = empty

        override fun clear() {
            cachedData = empty
        }

        override fun updateObserver(uiObserver: UiObserver<T>) {
            observer = uiObserver
            observer.update(cachedData)

        }

        override fun update(data: T) {
            cachedData = data
            observer.update(cachedData)

        }

    }
}

interface UiObserver<T : Any> : UiUpdate<T>{


    class Empty<T : Any>() : UiObserver<T> {
        override fun update(data: T) = Unit

        override fun equals(other: Any?): Boolean {
            return javaClass.name == other?.javaClass?.name
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

    }
}

interface UpdateObserver<T : Any> {
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())
}

interface UiUpdate<T : Any> {

    fun update(data: T)
}