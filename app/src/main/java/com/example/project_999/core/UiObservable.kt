package com.example.project_999.core

import androidx.annotation.MainThread

interface UiObservable<T : Any> : UiUpdate<T>, UpdateObserver<T> {
    fun clear()
    abstract class Single<T : Any>(
        private val empty: T
    ) : UiObservable<T> {

        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()

        @Volatile
        protected var cachedData: T = empty

        override fun clear() {
            cachedData = empty
        }

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(Single::class.java) {
            observer = uiObserver
            observer.update(cachedData)

        }

        override fun update(data: T) = synchronized(Single::class.java) {
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