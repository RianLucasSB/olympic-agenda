package com.boas.rian.olympicagenda.model

import java.time.LocalDateTime

data class Event(
    val id: Int,
    val day: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val status: String,
    val genderCode: String,
    val competitors: List<Competitor>,
    val disciplineImage: String,
    val disciplineName: String,
) {
}