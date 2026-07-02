package com.example.denniknotification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import com.example.denniknotification.NotificationHelper.sendNotification

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var refreshReceiver: BroadcastReceiver

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.logText)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnSend = findViewById<Button>(R.id.btnSend)

        NotificationHelper.createChannel(this)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        sendNotification(
            this,
            "Denník",
            "App spustená"
        )

        refresh(textView)

        btnDelete.setOnClickListener {
            LogStore.clear(this)
            refresh(textView)
        }

        btnSend.setOnClickListener {
            sendToApi()
        }

        refreshReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == NotificationHelper.ACTION_REFRESH_UI) {
                    refresh(textView)
                }
            }
        }

        registerReceiver(refreshReceiver, IntentFilter(NotificationHelper.ACTION_REFRESH_UI))
    }

    override fun onDestroy() {
        super.onDestroy()
        /* odregistrovať receiver, aby nedošlo k memory leak */
        try {
            unregisterReceiver(refreshReceiver)
        } catch (e: IllegalArgumentException) {
            // receiver už bol odregistrovaný
        }
    }

    override fun onResume() {
        super.onResume()
        refresh(textView)
    }

    private fun refresh(textView: TextView) {
        val logs = LogStore.readAll(this)
        textView.text = logs.joinToString("\n") { "${it.date} ${it.time}, ${it.category}" }
    }

    private fun sendToApi() {
        Thread {
            val logs = LogStore.readAll(this)

            val payload = logs.joinToString("\n") {
                "${it.date} ${it.time}, ${it.category}"
            }

            try {
                ApiClient.sendLog("http://SERVER/log", payload)
            } catch (e: Exception) {
                // zatiaľ ignor
            }
        }.start()
    }
}