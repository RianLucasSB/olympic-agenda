package com.boas.rian.olympicagenda.webclient.model

import com.boas.rian.olympicagenda.extensions.toLocalDateTime
import com.boas.rian.olympicagenda.model.Event
import com.boas.rian.olympicagenda.webclient.model.competitor.CompetitorResponse
import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("day") var day: String? = null,
    @SerializedName("discipline_name") var disciplineName: String? = null,
    @SerializedName("discipline_pictogram") var disciplinePictogram: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("venue_name") var venueName: String? = null,
    @SerializedName("event_name") var eventName: String? = null,
    @SerializedName("detailed_event_name") var detailedEventName: String? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("is_medal_event") var isMedalEvent: Int? = null,
    @SerializedName("is_live") var isLive: Int? = null,
    @SerializedName("gender_code") var genderCode: String? = null,
    @SerializedName("competitors") var competitors: ArrayList<CompetitorResponse> = arrayListOf()
) {
    val event: Event
        get() = Event(
            id ?: 0,
            day ?: "",
            startDate?.toLocalDateTime(),
            endDate?.toLocalDateTime(),
            status ?: "",
            genderCode ?: "",
            competitors.map { it.competitor }
        )
}
