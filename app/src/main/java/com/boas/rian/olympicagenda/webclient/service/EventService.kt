package com.boas.rian.olympicagenda.webclient.service

import com.boas.rian.olympicagenda.webclient.model.ApiResponse
import com.boas.rian.olympicagenda.webclient.model.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @GET("events?country=BRA&date=2024-08-08")
    suspend fun getAll(@Query("page") page: Int = 1): ApiResponse<EventResponse>
}