package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.roundToInt

class PremiumScorePanelRenderer(
    private val tileSize: Float
) {

    // ============================================================
    // PANEL CONFIG
    // ============================================================

    private val width =
        tileSize * 2.8f

    private val height =
        tileSize * 1.2f

    private val cornerRadius =
        tileSize * 0.28f

    private val margin =
        tileSize * 0.35f


    // ============================================================
    // PAINTS
    // ============================================================

    private val panelPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color =
                Color.HSVToColor(
                    floatArrayOf(
                        220f,
                        0.18f,
                        0.22f
                    )
                )
        }


    private val labelPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color =
                Color.argb(180,255,255,255)

            textAlign = Paint.Align.CENTER

            textSize =
                tileSize * 0.28f

            isFakeBoldText = true
        }


    private val scorePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize =
                tileSize * 0.48f

            isFakeBoldText = true
        }


    private val rect = RectF()


    // ============================================================
    // RENDER
    // ============================================================

    fun render(
        canvas: Canvas,
        score: Long
    ) {

        val left = margin

        val top = margin

        rect.set(
            left,
            top,
            left + width,
            top + height
        )


        // draw panel
        canvas.drawRoundRect(
            rect,
            cornerRadius,
            cornerRadius,
            panelPaint
        )


        // draw label
        val labelY =
            rect.top + tileSize * 0.38f

        canvas.drawText(
            "SCORE",
            rect.centerX(),
            labelY,
            labelPaint
        )


        // draw score value
        val valueY =
            rect.bottom - tileSize * 0.32f

        canvas.drawText(
            formatScore(score),
            rect.centerX(),
            valueY,
            scorePaint
        )
    }


    // ============================================================
    // FORMAT
    // ============================================================

    private fun formatScore(score: Long): String {

        return String.format("%,d", score)
    }
}
