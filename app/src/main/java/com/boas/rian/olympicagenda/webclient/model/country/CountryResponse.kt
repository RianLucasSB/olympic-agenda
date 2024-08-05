package com.boas.rian.olympicagenda.webclient.model.country

import com.boas.rian.olympicagenda.model.Country

class CountryResponse(
    val id: String,
    val name: String,
    val flag_url: String,
) {

    val country get() = Country(id, name, flag_url)
}