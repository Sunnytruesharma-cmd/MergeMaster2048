package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.roundToInt

class UIRenderer(
    private val screenWidth: Int,
    private val gridLeft: Float,
    private val gridTop: Float,
    private val tileSize: Float
) {

    // ============================================================
    // PANEL CONFIG
    // ============================================================

    private val panelHeight =
        tileSize * 1.2f

    private val panelWidth =
        tileSize * 2.4f

    private val panelRadius =
        tileSize * 0.25f


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


    private val titlePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize =
                tileSize * 0.55f

            isFakeBoldText = true
        }


    private val labelPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color =
                Color.argb(180,255,255,255)

            textAlign = Paint.Align.CENTER

            textSize =
                tileSize * 0.28f
        }


    private val valuePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize =
                tileSize * 0.42f

            isFakeBoldText = true
        }


    private val rect = RectF()


    // ============================================================
    // RENDER
    // ============================================================

    fun render(
        canvas: Canvas,
        nextTileValue: Int
    ) {

        drawTitle(canvas)

        drawNextTilePanel(canvas, nextTileValue)
    }


    // ============================================================
    // TITLE
    // ============================================================

    private fun drawTitle(canvas: Canvas) {

        val x =
            screenWidth / 2f

        val y =
            tileSize * 0.9f

        canvas.drawText(
            "MERGE MASTER",
            x,
            y,
            titlePaint
        )
    }


    // ============================================================
    // NEXT TILE PANEL
    // ============================================================

    private fun drawNextTilePanel(
        canvas: Canvas,
        nextTileValue: Int
    ) {

        val centerX =
            gridLeft + tileSize * 2.5f

        val top =
            gridTop - tileSize * 1.9f

        rect.set(
            centerX - panelWidth/2f,
            top,
            centerX + panelWidth/2f,
            top + panelHeight
        )

        canvas.drawRoundRect(
            rect,
            panelRadius,
            panelRadius,
            panelPaint
        )


        // label
        val labelY =
            rect.top + tileSize * 0.35f

        canvas.drawText(
            "NEXT",
            rect.centerX(),
            labelY,
            labelPaint
        )


        // value
        val valueY =
            rect.bottom - tileSize * 0.35f

        canvas.drawText(
            nextTileValue.toString(),
            rect.centerX(),
            valueY,
            valuePaint
        )
    }
}
