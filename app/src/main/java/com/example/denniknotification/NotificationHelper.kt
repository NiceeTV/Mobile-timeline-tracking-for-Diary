package com.example.denniknotification

import android.Manifest
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


object NotificationHelper {

    const val CHANNEL_ID = "daily_channel"
    const val ACTION_REFRESH_UI = "com.example.denniknotification.REFRESH_UI"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Denník notifikácie",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun getBroadcast(
        context: Context,
        category: String
    ): PendingIntent {

        val intent = Intent(context, CategoryReceiver::class.java).apply {
            putExtra("category", category)
        }

        return PendingIntent.getBroadcast(
            context,
            category.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun sendRefreshBroadcast(context: Context) {
        val intent = Intent(ACTION_REFRESH_UI)
        context.sendBroadcast(intent)
    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun sendNotification(context: Context, title: String, text: String) {

        val customView = RemoteViews(
            context.packageName,
            com.example.denniknotification.R.layout.notilay
        )



        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.anime,
            getBroadcast(context, "anime")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.praca,
            getBroadcast(context, "praca")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.skola,
            getBroadcast(context, "skola")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.citanie,
            getBroadcast(context, "citanie")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.jedlo,
            getBroadcast(context, "jedlo")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.domacnost,
            getBroadcast(context, "domacnost")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.sport,
            getBroadcast(context, "sport")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.social,
            getBroadcast(context, "social")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.skrol,
            getBroadcast(context, "skrol")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.hry,
            getBroadcast(context, "hry")
        )
        customView.setOnClickPendingIntent(
            com.example.denniknotification.R.id.other,
            getBroadcast(context, "other")
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(customView)
            .setCustomBigContentView(customView)
            .setCustomHeadsUpContentView(customView)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(1, notification)
    }
}

