package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class GridPanelRenderer(
    private val gridLeft: Float,
    private val gridTop: Float,
    private val cols: Int,
    private val rows: Int,
    private val tileSize: Float
) {

    // ============================================================
    // PANEL CONFIG (NO EXTRA PADDING)
    // ============================================================

    private val cornerRadius =
        tileSize * 0.35f

    // ============================================================
    // PANEL PAINT
    // ============================================================

    private val panelPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color =
                Color.HSVToColor(
                    floatArrayOf(
                        220f,
                        0.15f,
                        0.18f
                    )
                )
        }

    // ============================================================
    // EXACT BOARD RECT (NO SIDE GAP)
    // ============================================================

    private val panelRect =
        RectF(
            gridLeft,
            gridTop,
            gridLeft + cols * tileSize,
            gridTop + rows * tileSize
        )

    // ============================================================
    // DRAW
    // ============================================================

    fun render(canvas: Canvas) {

        canvas.drawRoundRect(
            panelRect,
            cornerRadius,
            cornerRadius,
            panelPaint
        )
    }
}