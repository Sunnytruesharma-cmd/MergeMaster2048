package com.firefinix.freestyle2048game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.ceil
import kotlin.math.pow

class HeartFlyAnimation(
    private val bitmap: Bitmap,
    private val startX: Float,
    private val startY: Float,
    private val endX: Float,
    private val endY: Float,
    private val reward: Int,
    private val onComplete: (Int) -> Unit
) {

    private var progress = 0f
    private val duration = 0.6f
    private var alive = true

    fun isAlive(): Boolean = alive

    fun update(dt: Float) {

        if (!alive) return

        progress += dt / duration

        if (progress >= 1f) {
            progress = 1f
            alive = false
            onComplete.invoke(reward)
        }
    }

    fun draw(canvas: Canvas) {

        if (!alive) return

        val t = easeOut(progress)

        val currentX = lerp(startX, endX, t)
        val currentY = lerp(startY, endY, t)

        val size = bitmap.width * 0.45f

        canvas.drawBitmap(
            bitmap,
            null,
            android.graphics.RectF(
                currentX - size / 2f,
                currentY - size / 2f,
                currentX + size / 2f,
                currentY + size / 2f
            ),
            Paint(Paint.ANTI_ALIAS_FLAG)
        )
    }

    private fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t
    }

    private fun easeOut(t: Float): Float {
        return 1f - (1f - t).pow(3)
    }
}