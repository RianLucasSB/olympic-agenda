package com.boas.rian.olympicagenda.repository

import android.util.Log
import com.boas.rian.olympicagenda.model.Event
import com.boas.rian.olympicagenda.webclient.EventWebClient
import com.boas.rian.olympicagenda.webclient.model.PaginationResponse
import java.time.LocalDate

class EventRepository(private val webClient: EventWebClient) {
    suspend fun getAll(page: Int = 1, date: LocalDate, countryId: String): PaginationResponse<List<Event>?>? {
        try {
            val events = webClient.getAll(page, date, countryId)
            return events
        } catch (e: Exception) {
            Log.e(TAG, "getAll: error finding countries", e)
            return null
        }
    }
}