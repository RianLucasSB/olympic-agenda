package com.boas.rian.olympicagenda.extensions

import android.widget.ImageView
import coil.load
import com.boas.rian.olympicagenda.R

fun ImageView.load(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    this.load(url) {
        placeholder(R.drawable.placeholder)
        error(R.drawable.erro)
        fallback(fallback)
    }
}