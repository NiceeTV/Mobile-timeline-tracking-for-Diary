package com.example.denniknotification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val events = LogStore.readAll(applicationContext)

        if (events.isEmpty()) return Result.success()

        val payload = events.joinToString("\n") {
            "${it.date} ${it.time}, ${it.category}"
        }

        return try {

            ApiClient.sendLog("http://SERVER/log", payload)

            LogStore.clear(applicationContext)

            Result.success()

        } catch (e: Exception) {

            // 1 krátky retry pokus
            return retryOnce(payload)
        }
    }

    private fun retryOnce(payload: String): Result {

        return try {

            Thread.sleep(5000) // krátke čakanie

            ApiClient.sendLog("http://SERVER/log", payload)

            LogStore.clear(applicationContext)

            Result.success()

        } catch (e: Exception) {
            Result.failure()
        }
    }
}