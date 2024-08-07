package com.boas.rian.olympicagenda.repository

import android.util.Log
import com.boas.rian.olympicagenda.model.Event
import com.boas.rian.olympicagenda.webclient.EventWebClient

class EventRepository(private val webClient: EventWebClient) {
    suspend fun getAll(): List<Event>? {
        try {
            val events = webClient.getAll()
            Log.i(TAG, "getAll: ${events}")
            return events
        } catch (e: Exception) {
            Log.e(TAG, "getAll: error finding countries", e)
            return null
        }
    }
}