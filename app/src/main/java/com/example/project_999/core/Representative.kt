package com.example.project_999.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface Representative<T : Any> {

    fun startGettingUpdates(callback: UiObserver<T>) = Unit
    fun stopGettingUpdates() = Unit

    abstract class Abstract<T : Any>(
        private val runAsync: RunAsync
    ) : Representative<T> {

        private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        protected fun <T: Any> handleAsync(
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            runAsync.runAsync(coroutineScope, backgroundBlock, uiBlock)
        }

        protected fun <T: Any> handleAsyncInternal(
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            runAsync.runAsyncInternal(backgroundBlock, uiBlock)
        }

        protected fun clear(){
            runAsync.clear()
        }
    }

}

