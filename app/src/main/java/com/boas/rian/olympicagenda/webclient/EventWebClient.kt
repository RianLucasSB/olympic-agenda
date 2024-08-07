package com.boas.rian.olympicagenda.webclient

import android.util.Log
import com.boas.rian.olympicagenda.model.Event

const val TAG = "EventWebClient"

class EventWebClient {
    private val service = RetrofitInit().eventService

    suspend fun getAll(): List<Event>? {
        return try {
            service.getAll().data.map { it.event }
        } catch (exception: Exception) {
            Log.e(TAG, "getAll: error listing all events", exception)
            null
        }
    }
}