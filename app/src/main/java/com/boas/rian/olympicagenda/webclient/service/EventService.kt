package com.boas.rian.olympicagenda.webclient.service

import com.boas.rian.olympicagenda.webclient.model.ApiResponse
import com.boas.rian.olympicagenda.webclient.model.EventResponse
import retrofit2.http.GET

interface EventService {
    @GET("events?country=BRA&date=2024-08-07")
    suspend fun getAll(): ApiResponse<EventResponse>
}