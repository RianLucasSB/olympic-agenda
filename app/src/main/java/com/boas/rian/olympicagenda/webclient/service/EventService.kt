package com.boas.rian.olympicagenda.webclient.service

import com.boas.rian.olympicagenda.webclient.model.ApiResponse
import com.boas.rian.olympicagenda.webclient.model.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @GET("events?country=BRA")
    suspend fun getAll(@Query("page") page: Int = 1, @Query("date") date: String): ApiResponse<EventResponse>
}