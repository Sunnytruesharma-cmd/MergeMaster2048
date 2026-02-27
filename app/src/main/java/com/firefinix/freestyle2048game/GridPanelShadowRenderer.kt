package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class GridPanelShadowRenderer(
    private val gridLeft: Float,
    private val gridTop: Float,
    private val cols: Int,
    private val rows: Int,
    private val tileSize: Float
) {

    private val cornerRadius = tileSize * 0.35f

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#0F0F15")
    }

    private val rect = RectF(
        gridLeft,
        gridTop,
        gridLeft + cols * tileSize,
        gridTop + rows * tileSize
    )

    fun render(canvas: Canvas) {
        canvas.drawRoundRect(
            rect,
            cornerRadius,
            cornerRadius,
            shadowPaint
        )
    }
}