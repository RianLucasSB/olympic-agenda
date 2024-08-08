package com.boas.rian.olympicagenda.webclient

import android.util.Log
import com.boas.rian.olympicagenda.model.Event
import com.boas.rian.olympicagenda.webclient.model.EventResponse
import com.boas.rian.olympicagenda.webclient.model.Meta
import com.boas.rian.olympicagenda.webclient.model.PaginationResponse

const val TAG = "EventWebClient"

class EventWebClient {
    private val service = RetrofitInit().eventService

    suspend fun getAll(page: Int = 1): PaginationResponse<List<Event>?>? {
        return try {
            val response = service.getAll(page)
            val meta = response.meta
            val data = response.data.map { it.event }
            PaginationResponse(data, meta)
        } catch (exception: Exception) {
            Log.e(TAG, "getAll: error listing all events", exception)
            null
        }
    }
}