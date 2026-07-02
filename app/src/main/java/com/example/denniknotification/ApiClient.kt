package com.example.denniknotification

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object ApiClient {

    private val client = OkHttpClient()

    fun sendLog(url: String, text: String) {

        val body = text.toRequestBody("text/plain".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            // zatiaľ ignorujeme response
        }
    }
}