package com.example.project_999.subscription.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.subscription.presentation.SubscriptionRepresentative

interface WorkManagerWrapper {

    fun start()

    class Base(
        private val workManager: WorkManager
    ): WorkManagerWrapper {
        override fun start() {
            val work = OneTimeWorkRequestBuilder<Worker>().build()
            workManager.beginUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.KEEP,
                work
            ).enqueue()
        }



    }
}

private const val WORK_NAME = "handle_async"

class Worker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters){
    override suspend fun doWork(): Result {
        var representative = (applicationContext as ProvideRepresentative)
            .provideRepresentative(SubscriptionRepresentative::class.java)
        representative.subscribeInternal()
        return Result.success()
    }

}