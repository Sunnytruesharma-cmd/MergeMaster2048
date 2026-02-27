package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.*

class ComboPopup(
    private val x: Float,
    private val y: Float,
    private val combo: Int
) {

    private var time = 0f
    private val duration = 0.8f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    fun update(dt: Float) {
        time += dt
    }

    fun draw(canvas: Canvas) {

        val progress = (time / duration).coerceAtMost(1f)

        // Elastic scale
        val scale = 1f + sin(progress * PI).toFloat() * (0.4f + combo * 0.08f)

        val alpha = ((1f - progress) * 255).toInt().coerceAtLeast(0)

        paint.alpha = alpha

        // Size grows with combo
        paint.textSize = 48f + combo * 8f

        // Color escalation
        paint.color = when {
            combo <= 2 -> Color.parseColor("#FFD700") // gold
            combo <= 4 -> Color.parseColor("#FFA500") // orange
            combo <= 6 -> Color.parseColor("#FF4500") // red-orange
            else -> Color.WHITE
        }

        canvas.save()
        canvas.translate(x, y - progress * 80f)
        canvas.scale(scale, scale)
        canvas.drawText("COMBO x$combo", 0f, 0f, paint)
        canvas.restore()
    }

    fun finished(): Boolean {
        return time >= duration
    }
}