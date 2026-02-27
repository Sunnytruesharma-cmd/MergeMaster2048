package com.firefinix.freestyle2048game

import android.graphics.*

class ButtonRenderer(
    private val tileSize: Float
) {

    private val paint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = ButtonStyle.iconColor

            textAlign = Paint.Align.CENTER

            textSize = tileSize * 0.42f

            isFakeBoldText = true
        }

    private val shadowPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = ButtonStyle.shadow

            maskFilter =
                BlurMaskFilter(
                    tileSize * 0.18f,
                    BlurMaskFilter.Blur.NORMAL
                )
        }

    private val highlightPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = ButtonStyle.highlight

            maskFilter =
                BlurMaskFilter(
                    tileSize * 0.12f,
                    BlurMaskFilter.Blur.NORMAL
                )
        }


    fun drawCapsuleButton(
        canvas: Canvas,
        rect: RectF,
        text: String,
        pressed: Boolean
    ) {

        val radius =
            rect.height() / 2f

        drawShadow(canvas, rect, radius)

        paint.color =
            if (pressed)
                ButtonStyle.backgroundPressed
            else
                ButtonStyle.background

        canvas.drawRoundRect(
            rect,
            radius,
            radius,
            paint
        )

        drawHighlight(canvas, rect, radius)

        drawText(canvas, rect, text)
    }


    fun drawSquareButton(
        canvas: Canvas,
        rect: RectF,
        icon: String,
        pressed: Boolean
    ) {

        val radius =
            tileSize * 0.28f

        drawShadow(canvas, rect, radius)

        paint.color =
            if (pressed)
                ButtonStyle.backgroundPressed
            else
                ButtonStyle.background

        canvas.drawRoundRect(
            rect,
            radius,
            radius,
            paint
        )

        drawHighlight(canvas, rect, radius)

        drawText(canvas, rect, icon)
    }


    private fun drawShadow(
        canvas: Canvas,
        rect: RectF,
        radius: Float
    ) {

        val shadowRect =
            RectF(
                rect.left + tileSize * 0.06f,
                rect.top + tileSize * 0.06f,
                rect.right + tileSize * 0.06f,
                rect.bottom + tileSize * 0.06f
            )

        canvas.drawRoundRect(
            shadowRect,
            radius,
            radius,
            shadowPaint
        )
    }


    private fun drawHighlight(
        canvas: Canvas,
        rect: RectF,
        radius: Float
    ) {

        val highlightRect =
            RectF(
                rect.left - tileSize * 0.03f,
                rect.top - tileSize * 0.03f,
                rect.right - tileSize * 0.03f,
                rect.bottom - tileSize * 0.03f
            )

        canvas.drawRoundRect(
            highlightRect,
            radius,
            radius,
            highlightPaint
        )
    }


    private fun drawText(
        canvas: Canvas,
        rect: RectF,
        text: String
    ) {

        val cx =
            rect.centerX()

        val cy =
            rect.centerY() -
                    (textPaint.descent() +
                            textPaint.ascent()) / 2

        canvas.drawText(
            text,
            cx,
            cy,
            textPaint
        )
    }
}
