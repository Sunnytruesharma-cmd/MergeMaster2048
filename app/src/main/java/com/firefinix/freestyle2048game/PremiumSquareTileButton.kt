package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.min

class PremiumSquareTileButton(

    private val context: Context,
    private val centerX: Float,
    private val centerY: Float,
    private val size: Float,
    private val type: ButtonType

) {

    // ============================================================
    // BUTTON TYPES
    // ============================================================

    enum class ButtonType {

        HAMMER,
        SWAP,
        UNDO,
        INFO
    }


    // ============================================================
    // STATE
    // ============================================================

    private var scale = 1f
    private var targetScale = 1f

    private var glow = 0f
    private var targetGlow = 0f


    // ============================================================
    // RECT
    // ============================================================

    private val rect = RectF()
    private val glowRect = RectF()

    private val radius =
        size * 0.22f


    // ============================================================
    // PAINTS
    // ============================================================

    private val fillPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    private val glowPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)


    // ============================================================
    // ICON
    // ============================================================

    private val icon: Drawable?


    init {

        fillPaint.color =
            when (type) {

                ButtonType.HAMMER ->
                    Color.parseColor("#FF7043")

                ButtonType.SWAP ->
                    Color.parseColor("#42A5F5")

                ButtonType.UNDO ->
                    Color.parseColor("#66BB6A")

                ButtonType.INFO ->
                    Color.parseColor("#AB47BC")
            }

        glowPaint.color = fillPaint.color


        icon =
            when (type) {

                ButtonType.HAMMER ->
                    context.getDrawable(R.drawable.ic_hammer_premium)

                ButtonType.SWAP ->
                    context.getDrawable(R.drawable.ic_swap_premium)

                ButtonType.UNDO ->
                    context.getDrawable(R.drawable.ic_undo_premium)

                ButtonType.INFO ->
                    context.getDrawable(R.drawable.ic_info_premium)
            }
    }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        val speed = 14f

        scale += (targetScale - scale) * speed * dt

        glow += (targetGlow - glow) * speed * dt
    }


    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        val half =
            size * 0.5f * scale

        rect.set(
            centerX - half,
            centerY - half,
            centerX + half,
            centerY + half
        )


        glowRect.set(rect)

        glowRect.inset(
            -glow * 12f,
            -glow * 12f
        )


        glowPaint.alpha =
            (glow * 140).toInt()


        canvas.drawRoundRect(
            glowRect,
            radius,
            radius,
            glowPaint
        )


        canvas.drawRoundRect(
            rect,
            radius,
            radius,
            fillPaint
        )


        drawIcon(canvas)
    }


    // ============================================================
    // DRAW ICON
    // ============================================================

    private fun drawIcon(canvas: Canvas) {

        val drawable =
            icon ?: return


        val iconSize =
            size * 0.55f * scale


        val left =
            (centerX - iconSize * 0.5f).toInt()

        val top =
            (centerY - iconSize * 0.5f).toInt()

        val right =
            (centerX + iconSize * 0.5f).toInt()

        val bottom =
            (centerY + iconSize * 0.5f).toInt()


        drawable.setBounds(
            left,
            top,
            right,
            bottom
        )


        drawable.draw(canvas)
    }


    // ============================================================
    // TOUCH
    // ============================================================

    fun contains(
        x: Float,
        y: Float
    ): Boolean {

        return rect.contains(x, y)
    }


    fun press() {

        targetScale = 0.9f

        targetGlow = 1f
    }


    fun release() {

        targetScale = 1f

        targetGlow = 0f
    }

}