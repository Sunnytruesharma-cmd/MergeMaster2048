package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.*

class Tile(
    var column: Int,
    var row: Int,
    var value: Int,
    private val size: Float,
    private val startX: Float,
    private val startY: Float,
    private val targetY: Float
){

    var drawX = startX
    var drawY = startY

    private var startAnimY = startY
    private var animTime = 0f
    private val animDuration = 0.14f
    private var moving = true

    fun isSettled(): Boolean {
        return !moving
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }
    fun update(dt: Float) {
        if (!moving) return

        animTime += dt
        var t = animTime / animDuration
        if (t >= 1f) {
            t = 1f
            moving = false
        }

        val eased = if (t < 0.5f)
            4f * t * t * t
        else
            1f - (-2f * t + 2f).pow(3) / 2f

        drawY = startAnimY + (targetY - startAnimY) * eased
    }

    fun draw(canvas: Canvas) {

        val rect = RectF(
            drawX,
            drawY,
            drawX + size,
            drawY + size
        )

        paint.color =
            if (value == 2) Color.parseColor("#FF6EC7")
            else Color.parseColor("#9B5DE5")

        canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, paint)

        textPaint.textSize = size * 0.38f
        textPaint.color = Color.BLACK

        val cx = rect.centerX()
        val cy = rect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2

        canvas.drawText(value.toString(), cx, cy, textPaint)
    }
}