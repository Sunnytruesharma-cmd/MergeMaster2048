package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.max
import kotlin.math.min

class PremiumIconButton(

    private val centerX: Float,
    private val centerY: Float,
    private val radius: Float,
    private val iconType: IconType

) {

    enum class IconType {
        PAUSE,
        UNDO,
        HAMMER,
        SWAP
    }

    // ============================================================
    // STATE
    // ============================================================

    private var pressed = false

    private var scale = 1f

    private var targetScale = 1f

    private var glow = 0f

    private var targetGlow = 0f


    // ============================================================
    // PAINTS
    // ============================================================

    private val circlePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.argb(220, 35, 35, 45)
        }

    private val glowPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE
            alpha = 0
        }

    private val iconPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE
            strokeWidth = radius * 0.18f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        val speed = 12f

        scale += (targetScale - scale) * speed * dt

        glow += (targetGlow - glow) * speed * dt
    }


    // ============================================================
    // DRAW
    // ============================================================

    fun render(canvas: Canvas) {

        val s = scale

        val r = radius * s

        glowPaint.alpha = (glow * 120).toInt()

        canvas.drawCircle(
            centerX,
            centerY,
            r * 1.35f,
            glowPaint
        )

        canvas.drawCircle(
            centerX,
            centerY,
            r,
            circlePaint
        )

        drawIcon(canvas, r)
    }


    // ============================================================
    // ICON DRAW
    // ============================================================

    private fun drawIcon(canvas: Canvas, r: Float) {

        when (iconType) {

            IconType.PAUSE -> {

                val w = r * 0.35f

                canvas.drawLine(
                    centerX - w,
                    centerY - r * 0.5f,
                    centerX - w,
                    centerY + r * 0.5f,
                    iconPaint
                )

                canvas.drawLine(
                    centerX + w,
                    centerY - r * 0.5f,
                    centerX + w,
                    centerY + r * 0.5f,
                    iconPaint
                )
            }

            IconType.UNDO -> {

                val path = Path()

                path.moveTo(centerX - r * 0.4f, centerY)

                path.lineTo(centerX, centerY - r * 0.4f)

                path.lineTo(centerX, centerY - r * 0.15f)

                path.cubicTo(
                    centerX + r * 0.4f,
                    centerY - r * 0.15f,
                    centerX + r * 0.4f,
                    centerY + r * 0.35f,
                    centerX - r * 0.1f,
                    centerY + r * 0.35f
                )

                canvas.drawPath(path, iconPaint)
            }

            IconType.HAMMER -> {

                canvas.drawLine(
                    centerX - r * 0.4f,
                    centerY + r * 0.4f,
                    centerX + r * 0.4f,
                    centerY - r * 0.4f,
                    iconPaint
                )

                canvas.drawLine(
                    centerX - r * 0.2f,
                    centerY - r * 0.4f,
                    centerX + r * 0.3f,
                    centerY - r * 0.4f,
                    iconPaint
                )
            }

            IconType.SWAP -> {

                canvas.drawLine(
                    centerX - r * 0.4f,
                    centerY - r * 0.2f,
                    centerX + r * 0.4f,
                    centerY - r * 0.2f,
                    iconPaint
                )

                canvas.drawLine(
                    centerX + r * 0.2f,
                    centerY - r * 0.4f,
                    centerX + r * 0.4f,
                    centerY - r * 0.2f,
                    iconPaint
                )

                canvas.drawLine(
                    centerX - r * 0.4f,
                    centerY + r * 0.2f,
                    centerX + r * 0.4f,
                    centerY + r * 0.2f,
                    iconPaint
                )

                canvas.drawLine(
                    centerX - r * 0.2f,
                    centerY + r * 0.4f,
                    centerX - r * 0.4f,
                    centerY + r * 0.2f,
                    iconPaint
                )
            }
        }
    }


    // ============================================================
    // TOUCH
    // ============================================================

    fun contains(x: Float, y: Float): Boolean {

        val dx = x - centerX

        val dy = y - centerY

        return dx * dx + dy * dy <= radius * radius
    }


    fun press() {

        pressed = true

        targetScale = 0.88f

        targetGlow = 1f
    }


    fun release() {

        pressed = false

        targetScale = 1f

        targetGlow = 0f
    }


    fun isPressed(): Boolean {

        return pressed
    }

}
