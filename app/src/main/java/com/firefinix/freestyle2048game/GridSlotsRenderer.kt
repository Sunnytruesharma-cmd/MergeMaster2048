package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class GridSlotsRenderer(
    private val gridLeft: Float,
    private val gridTop: Float,
    private val cols: Int,
    private val rows: Int,
    private val tileSize: Float
) {

    // ============================================================
    // SLOT CONFIG (NO GAP)
    // ============================================================

    private val renderSize = tileSize

    private val cornerRadius =
        tileSize * 0.22f

    // ============================================================
    // SLOT PAINT
    // ============================================================

    private val slotPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            // Slightly darker so it merges visually with panel
            color =
                Color.argb(
                    40,
                    255,
                    255,
                    255
                )
        }

    // ============================================================
    // RECT REUSE
    // ============================================================

    private val rect = RectF()

    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        for (row in 0 until rows) {

            for (col in 0 until cols) {

                val x =
                    gridLeft +
                            col * tileSize

                val y =
                    gridTop +
                            row * tileSize

                rect.set(
                    x,
                    y,
                    x + renderSize,
                    y + renderSize
                )

                canvas.drawRoundRect(
                    rect,
                    cornerRadius,
                    cornerRadius,
                    slotPaint
                )
            }
        }
    }
}