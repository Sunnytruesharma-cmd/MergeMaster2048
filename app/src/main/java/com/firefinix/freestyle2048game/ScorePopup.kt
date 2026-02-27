package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ScorePopup(

    private val text: String,
    private val x: Float,
    private var y: Float,
    tileSize: Float

) {

    private var alpha = 255f

    private var timer = 0f

    private val lifetime = 0.6f

    private val speed = tileSize * 1.2f

    private val paint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = tileSize * 0.32f
            isFakeBoldText = true
        }

    fun update(dt: Float) {

        timer += dt

        y -= speed * dt

        alpha = 255f * (1f - timer / lifetime)

        if (alpha < 0f)
            alpha = 0f
    }

    fun draw(canvas: Canvas) {

        paint.alpha = alpha.toInt()

        canvas.drawText(text, x, y, paint)
    }

    fun finished(): Boolean {

        return timer >= lifetime
    }
}
