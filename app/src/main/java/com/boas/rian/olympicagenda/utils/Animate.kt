package com.boas.rian.olympicagenda.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator


class Animate {
    companion object {
        fun animateColorChange(view: View, startColor: Int, endColor: Int) {
            val colorAnimator = ValueAnimator.ofArgb(startColor, endColor)
            colorAnimator.setDuration(300)
            colorAnimator.interpolator = LinearInterpolator()

            colorAnimator.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }

            colorAnimator.start()
        }
    }
}