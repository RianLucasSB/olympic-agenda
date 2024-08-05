package com.boas.rian.olympicagenda.repository

import android.util.Log
import com.boas.rian.olympicagenda.webclient.model.country.CountryResponse
import com.boas.rian.olympicagenda.webclient.service.CountryService

const val TAG = "CountryRepository"

class CountryRepository(private val webClient: CountryService) {
    suspend fun getAll(): List<CountryResponse>? {
        try {
            val countries = webClient.getAll()
            Log.i(TAG, "getAll: ${countries}")
            return countries.data
        }catch (e: Exception){
            Log.e(TAG, "getAll: error finding countries", e)
            return null
        }
    }
}