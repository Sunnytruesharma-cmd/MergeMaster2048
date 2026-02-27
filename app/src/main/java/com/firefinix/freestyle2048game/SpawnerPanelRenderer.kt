package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.ln
import kotlin.math.sin

class SpawnerPanelRenderer(
    private val gridLeft: Float,
    private val gridTop: Float,
    private val tileSize: Float
) {

    // ============================================================
    // FIXED POSITION (CENTER OF GRID)
    // ============================================================

    private val fixedCenterX =
        gridLeft + (tileSize * 5f) / 2f   // COLS = 5 safe fixed center

    private val baseCenterY =
        gridTop - tileSize * 0.9f

    private val shadowBaseY =
        gridTop - tileSize * 0.25f


    // ============================================================
    // SIZE
    // ============================================================

    private val containerSize =
        tileSize * 1.1f

    private val spawnTileSize =
        tileSize * 0.85f

    private val containerRadius =
        tileSize * 0.30f

    private val tileRadius =
        tileSize * 0.25f


    // ============================================================
    // ANIMATION STATE
    // ============================================================

    private var currentScale = 1f

    private var glowTime = 0f

    private var hoverTime = 0f

    private val glowSpeed = 6f

    private val hoverSpeed = 2.5f


    // ============================================================
    // PAINTS
    // ============================================================

    private val containerPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color =
                Color.HSVToColor(
                    floatArrayOf(220f, 0.18f, 0.22f)
                )
        }

    private val borderPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            style = Paint.Style.STROKE

            strokeWidth =
                tileSize * 0.06f

            color =
                Color.argb(90, 255, 255, 255)
        }

    private val tilePaint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    private val glowPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    private val shadowPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            maskFilter =
                BlurMaskFilter(
                    tileSize * 0.18f,
                    BlurMaskFilter.Blur.NORMAL
                )
        }

    private val textPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize =
                spawnTileSize * 0.45f

            isFakeBoldText = true
        }


    private val rect = RectF()

    private val tileRect = RectF()


    // ============================================================
    // UPDATE ANIMATION
    // ============================================================

    private fun updateAnimation(isTouching: Boolean) {

        val targetScale =
            if (isTouching) 1.15f else 1f

        currentScale +=
            (targetScale - currentScale) * 0.18f

        if (isTouching)
            glowTime += glowSpeed * 0.016f

        hoverTime += hoverSpeed * 0.016f
    }


    // ============================================================
    // RENDER (HYBRID MODE)
    // ============================================================

    fun render(
        canvas: Canvas,
        value: Int,
        visualColumn: Float, // not used for position now
        isTouching: Boolean
    ) {

        updateAnimation(isTouching)

        val hoverOffset =
            sin(hoverTime) *
                    tileSize * 0.06f

        val centerX =
            fixedCenterX

        val centerY =
            baseCenterY + hoverOffset

        val shadowY =
            shadowBaseY +
                    hoverOffset * 0.35f


        val scaledContainer =
            containerSize * currentScale

        val scaledTile =
            spawnTileSize * currentScale


        drawShadow(
            canvas,
            centerX,
            shadowY,
            scaledTile,
            isTouching
        )

        drawGlow(
            canvas,
            centerX,
            centerY,
            scaledTile,
            isTouching
        )

        drawContainer(
            canvas,
            centerX,
            centerY,
            scaledContainer
        )

        drawTile(
            canvas,
            centerX,
            centerY,
            scaledTile,
            value
        )
    }


    // ============================================================
    // SHADOW
    // ============================================================

    private fun drawShadow(
        canvas: Canvas,
        centerX: Float,
        shadowY: Float,
        scaledTile: Float,
        isTouching: Boolean
    ) {

        val width =
            scaledTile * 0.85f

        val height =
            scaledTile * 0.22f

        val alpha =
            if (isTouching) 120 else 80

        shadowPaint.color =
            Color.argb(alpha, 0, 0, 0)

        val shadowRect =
            RectF(
                centerX - width / 2f,
                shadowY - height / 2f,
                centerX + width / 2f,
                shadowY + height / 2f
            )

        canvas.drawOval(shadowRect, shadowPaint)
    }


    // ============================================================
    // GLOW
    // ============================================================

    private fun drawGlow(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        scaledTile: Float,
        isTouching: Boolean
    ) {

        if (!isTouching) return

        val pulse =
            0.5f + 0.5f * sin(glowTime)

        val radius =
            scaledTile * (1.4f + pulse * 0.2f)

        glowPaint.shader =
            RadialGradient(
                centerX,
                centerY,
                radius,
                intArrayOf(
                    Color.argb(
                        (80 * pulse).toInt(),
                        255, 255, 255
                    ),
                    Color.TRANSPARENT
                ),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP
            )

        canvas.drawCircle(
            centerX,
            centerY,
            radius,
            glowPaint
        )
    }


    // ============================================================
    // CONTAINER
    // ============================================================

    private fun drawContainer(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        scaledContainer: Float
    ) {

        rect.set(
            centerX - scaledContainer / 2f,
            centerY - scaledContainer / 2f,
            centerX + scaledContainer / 2f,
            centerY + scaledContainer / 2f
        )

        canvas.drawRoundRect(
            rect,
            containerRadius,
            containerRadius,
            containerPaint
        )

        canvas.drawRoundRect(
            rect,
            containerRadius,
            containerRadius,
            borderPaint
        )
    }


    // ============================================================
    // TILE
    // ============================================================

    private fun drawTile(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        scaledTile: Float,
        value: Int
    ) {

        tileRect.set(
            centerX - scaledTile / 2f,
            centerY - scaledTile / 2f,
            centerX + scaledTile / 2f,
            centerY + scaledTile / 2f
        )

        tilePaint.color =
            getTileColor(value)

        canvas.drawRoundRect(
            tileRect,
            tileRadius,
            tileRadius,
            tilePaint
        )

        val cx = tileRect.centerX()

        val cy =
            tileRect.centerY() -
                    (textPaint.descent() +
                            textPaint.ascent()) / 2f

        canvas.drawText(
            value.toString(),
            cx,
            cy,
            textPaint
        )
    }


    private fun getTileColor(value: Int): Int {

        val hue =
            (ln(value.toFloat()) /
                    ln(2f) * 35f) % 360f

        return Color.HSVToColor(
            floatArrayOf(
                hue,
                0.7f,
                0.95f
            )
        )
    }
}
