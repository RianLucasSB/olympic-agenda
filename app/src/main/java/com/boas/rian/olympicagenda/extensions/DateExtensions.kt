package com.boas.rian.olympicagenda.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


private val isoFormatter = DateTimeFormatter
    .ofPattern("dd/MM/yyyy", Locale("UTC"))

fun LocalDateTime.toFormattedString(): String = this.format(isoFormatter)
fun LocalDate.toFormattedString(): String = this.format(isoFormatter)


fun String.toLocalDateTime(): LocalDateTime {
    val zonedDateTime = ZonedDateTime.parse(this)
    val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    return localDateTime
}