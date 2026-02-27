package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Color

class AdsBarRenderer(

    private val screenWidth: Int,
    private val screenHeight: Int,
    private val tileSize: Float

) {

    // ============================================================
    // LAYOUT
    // ============================================================

    private val barHeight =
        tileSize * 1.2f

    private val topY =
        screenHeight - barHeight

    private val rect =
        RectF(
            0f,
            topY,
            screenWidth.toFloat(),
            screenHeight.toFloat()
        )


    // ============================================================
    // BACKGROUND PAINT
    // ============================================================

    private val backgroundPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.argb(180, 15, 15, 25)
        }


    // ============================================================
    // BORDER PAINT
    // ============================================================

    private val borderPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            style = Paint.Style.STROKE

            strokeWidth = 2f

            color = Color.argb(120, 255, 255, 255)
        }


    // ============================================================
    // TEXT PAINT (placeholder)
    // ============================================================

    private val textPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.argb(140, 255, 255, 255)

            textAlign = Paint.Align.CENTER

            textSize = tileSize * 0.42f

            isFakeBoldText = true
        }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        // No animation required currently
        // Reserved for future ad animations
    }


    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        // background

        canvas.drawRect(
            rect,
            backgroundPaint
        )


        // border

        canvas.drawRect(
            rect,
            borderPaint
        )


        // placeholder text

        val cx =
            rect.centerX()

        val cy =
            rect.centerY() -
                    (textPaint.descent() +
                            textPaint.ascent()) * 0.5f


        canvas.drawText(
            "Ad Space",
            cx,
            cy,
            textPaint
        )
    }


    // ============================================================
    // HEIGHT ACCESS
    // ============================================================

    fun getHeight(): Float {

        return barHeight
    }


    // ============================================================
    // TOP POSITION ACCESS
    // ============================================================

    fun getTopY(): Float {

        return topY
    }

}
