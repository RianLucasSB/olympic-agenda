package com.boas.rian.olympicagenda.ui.dialog

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

class DatePickerDialog() {
    fun show(
        supportFragmentManager: FragmentManager,
        onSelectDate: (date: LocalDate) -> Unit
    ) {
        val datePicker = MaterialDatePicker
            .Builder.datePicker()
            .build()

        datePicker.addOnPositiveButtonClickListener { dateInMs ->
            val data = Instant.ofEpochMilli(dateInMs)
                .atZone(ZoneId.of(ZoneId.systemDefault().id))
                .withZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
                .toLocalDate()

            onSelectDate(data)
        }
        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
    }
}
