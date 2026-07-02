package com.example.denniknotification

import android.content.Context
import com.google.gson.Gson

data class LogEvent(
    val date: String,
    val time: String,
    val category: String
)

object LogStore {

    private const val FILE = "timeline.jsonl"
    private val gson = Gson()

    fun append(context: Context, event: LogEvent) {

        val json = gson.toJson(event)

        context.openFileOutput(FILE, Context.MODE_APPEND).use {
            it.write((json + "\n").toByteArray())
        }
    }

    fun readAll(context: Context): List<LogEvent> {
        return try {
            context.openFileInput(FILE)
                .bufferedReader()
                .readLines()
                .mapNotNull { gson.fromJson(it, LogEvent::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun readLast(context: Context): LogEvent? {
        val logs = readAll(context)
        return logs.lastOrNull()
    }

    fun clear(context: Context) {
        context.deleteFile(FILE)
    }
}