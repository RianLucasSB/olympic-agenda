package com.boas.rian.olympicagenda.extensions

import android.content.Context
import android.content.Intent

fun Context.navigate(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}