package com.boas.rian.olympicagenda.webclient

import android.util.Log
import com.boas.rian.olympicagenda.model.Event
import com.boas.rian.olympicagenda.webclient.model.EventResponse
import com.boas.rian.olympicagenda.webclient.model.Meta
import com.boas.rian.olympicagenda.webclient.model.PaginationResponse
import java.time.LocalDate
import java.time.LocalDateTime

const val TAG = "EventWebClient"

class EventWebClient {
    private val service = RetrofitInit().eventService

    suspend fun getAll(page: Int = 1, date: LocalDate): PaginationResponse<List<Event>?>? {
        return try {
            Log.i(TAG, "getAll: $date")
            val response = service.getAll(page, date.toString())
            val meta = response.meta
            val data = response.data.map { it.event }
            PaginationResponse(data, meta)
        } catch (exception: Exception) {
            Log.e(TAG, "getAll: error listing all events", exception)
            null
        }
    }
}