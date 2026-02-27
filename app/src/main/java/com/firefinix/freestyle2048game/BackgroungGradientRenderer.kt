package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

class BackgroundGradientRenderer(
    screenHeight: Int
) {

    // ============================================================
    // PAINT
    // ============================================================

    private val paint = Paint()

    // ============================================================
    // GRADIENT
    // ============================================================

    private val gradient =
        LinearGradient(
            0f,
            0f,
            0f,
            screenHeight.toFloat(),

            Color.rgb(32, 34, 40),  // top color
            Color.rgb(12, 14, 18),  // bottom color

            Shader.TileMode.CLAMP
        )


    init {

        paint.shader = gradient
    }


    // ============================================================
    // DRAW
    // ============================================================

    fun render(canvas: Canvas) {

        canvas.drawRect(
            0f,
            0f,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            paint
        )
    }
}
