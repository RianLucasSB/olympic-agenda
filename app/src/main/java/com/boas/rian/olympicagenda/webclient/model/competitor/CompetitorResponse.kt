package com.boas.rian.olympicagenda.webclient.model.competitor

import com.boas.rian.olympicagenda.model.Competitor
import com.google.gson.annotations.SerializedName

data class CompetitorResponse(
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("country_flag_url") var countryFlagUrl: String? = null,
    @SerializedName("competitor_name") var competitorName: String? = null,
    @SerializedName("position") var position: Int? = null,
    @SerializedName("result_position") var resultPosition: String? = null,
    @SerializedName("result_mark") var resultMark: String? = null
) {

    val competitor: Competitor
        get() = Competitor(countryId ?: "", countryFlagUrl?: "", competitorName?: "")
}