package com.boas.rian.olympicagenda.webclient

import com.boas.rian.olympicagenda.webclient.service.CountryService
import com.boas.rian.olympicagenda.webclient.service.EventService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInit {
    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://apis.codante.io/olympic-games/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val countryService = retrofit.create(CountryService::class.java)
    val eventService = retrofit.create(EventService::class.java)
}