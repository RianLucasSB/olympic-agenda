package com.boas.rian.olympicagenda.webclient.service

import com.boas.rian.olympicagenda.webclient.model.ApiResponse
import com.boas.rian.olympicagenda.webclient.model.country.CountryResponse
import retrofit2.http.GET

interface CountryService {
    @GET("countries")
    suspend fun getAll(): ApiResponse<CountryResponse>
}