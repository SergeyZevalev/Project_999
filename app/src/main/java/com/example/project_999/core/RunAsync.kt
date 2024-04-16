package com.example.project_999.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync{

    fun <T: Any> runAsync(
        scope: CoroutineScope,
        backgroundBlock: suspend () -> T,
        uiBlock: (T) -> Unit
    )

    suspend fun <T: Any> runAsyncInternal(
        backgroundBlock: suspend () -> T,
        uiBlock: (T) -> Unit
    )

    fun clear()

    class Base(
        private val dispatchersList: DispatchersList
    ) : RunAsync {

        private var job: Job? = null

        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            job = scope.launch(dispatchersList.background()) {
                val result = backgroundBlock.invoke()
                withContext(dispatchersList.ui()) {
                    uiBlock.invoke(result)
                }
            }
        }

        override suspend fun <T : Any> runAsyncInternal(backgroundBlock: suspend () -> T, uiBlock: (T) -> Unit) {
            withContext(dispatchersList.background()) {
                val result = backgroundBlock.invoke()
                withContext(dispatchersList.ui()){
                    uiBlock.invoke(result)
                }
            }
        }

        override fun clear() {
            job?.cancel()
            job = null
        }
    }
}