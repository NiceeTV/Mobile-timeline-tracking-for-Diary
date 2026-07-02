package com.example.denniknotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CategoryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val category = intent.getStringExtra("category") ?: return

        val now = LocalDateTime.now()

        val event = LogEvent(
            date = now.toLocalDate().toString(),
            time = now.toLocalTime().format(
                DateTimeFormatter.ofPattern("HH:mm")
            ),
            category = category
        )

        LogStore.append(context, event)
        NotificationHelper.sendRefreshBroadcast(context)
    }
}