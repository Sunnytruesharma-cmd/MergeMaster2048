package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GemPopup(

    private val text: String,
    startX: Float,
    startY: Float,
    tileSize: Float

) {

    private var x = startX
    private var y = startY

    private var life = 0f

    private val duration = 0.8f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color = Color.parseColor("#00E5FF")

        textAlign = Paint.Align.CENTER

        textSize = tileSize * 0.32f

        isFakeBoldText = true
    }

    fun update(dt: Float) {

        life += dt

        y -= dt * tileSizeSpeed
    }

    fun draw(canvas: Canvas) {

        val alpha = ((1f - life / duration) * 255).toInt()

        paint.alpha = alpha.coerceIn(0, 255)

        canvas.drawText(text, x, y, paint)
    }

    fun finished(): Boolean {

        return life >= duration
    }

    companion object {

        private const val tileSizeSpeed = 120f
    }
}