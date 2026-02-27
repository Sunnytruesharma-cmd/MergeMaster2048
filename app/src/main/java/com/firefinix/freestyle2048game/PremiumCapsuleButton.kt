package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat

class PremiumCapsuleButton(

    private val context: Context,
    private val view: View,

    private val centerX: Float,
    private val centerY: Float,

    private val screenWidth: Float,

    private val iconRes: Int

) {

    // ============================================================
    // SIZE
    // ============================================================

    private val width = screenWidth * 0.34f
    private val height = screenWidth * 0.115f

    private val radius = height * 0.5f

    private val rect = RectF(
        centerX - width / 2f,
        centerY - height / 2f,
        centerX + width / 2f,
        centerY + height / 2f
    )

    // Plus button
    private val plusRadius = height * 0.32f

    private val plusCenterX = rect.right - plusRadius * 1.4f
    private val plusCenterY = rect.centerY()


    // ============================================================
    // BITMAPS
    // ============================================================

    private val iconBitmap: Bitmap =
        loadBitmap(iconRes)

    private val plusBitmap: Bitmap =
        loadBitmap(R.drawable.ic_add_premium)


    // ============================================================
    // PAINTS
    // ============================================================

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color = Color.parseColor("#2A2A3A")
    }

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        style = Paint.Style.STROKE

        strokeWidth = screenWidth * 0.006f

        color = Color.parseColor("#505070")
    }

    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        isFilterBitmap = true
        isDither = true
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color = Color.WHITE

        textSize = height * 0.42f

        typeface = Typeface.DEFAULT_BOLD

        textAlign = Paint.Align.LEFT
    }

    private val plusBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color = Color.parseColor("#19C37D")
    }


    // ============================================================
    // STATE
    // ============================================================

    private var currencyValue: Long = 0

    private var pressed = false

    private var pressScale = 1f


    // ============================================================
    // PUBLIC VALUE UPDATE
    // ============================================================

    fun setValue(value: Long) {

        currencyValue = value
    }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        val targetScale =
            if (pressed) 0.94f else 1f

        pressScale += (targetScale - pressScale) * 18f * dt
    }


    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        canvas.save()

        canvas.scale(
            pressScale,
            pressScale,
            rect.centerX(),
            rect.centerY()
        )

        // Background
        canvas.drawRoundRect(
            rect,
            radius,
            radius,
            bgPaint
        )

        // Border
        canvas.drawRoundRect(
            rect,
            radius,
            radius,
            borderPaint
        )

        // Icon
        val iconSize = height * 0.62f

        val iconLeft = rect.left + height * 0.22f

        val iconTop = rect.centerY() - iconSize / 2f

        val iconRect = RectF(
            iconLeft,
            iconTop,
            iconLeft + iconSize,
            iconTop + iconSize
        )

        canvas.drawBitmap(
            iconBitmap,
            null,
            iconRect,
            iconPaint
        )

        // Currency text
        val textX = iconRect.right + height * 0.22f

        val textY =
            rect.centerY() -
                    ((textPaint.descent() + textPaint.ascent()) / 2)

        canvas.drawText(
            currencyValue.toString(),
            textX,
            textY,
            textPaint
        )

        // Plus button background
        canvas.drawCircle(
            plusCenterX,
            plusCenterY,
            plusRadius,
            plusBgPaint
        )

        // Plus icon
        val plusSize = plusRadius * 1.2f

        val plusRect = RectF(
            plusCenterX - plusSize / 2f,
            plusCenterY - plusSize / 2f,
            plusCenterX + plusSize / 2f,
            plusCenterY + plusSize / 2f
        )

        canvas.drawBitmap(
            plusBitmap,
            null,
            plusRect,
            iconPaint
        )

        canvas.restore()
    }


    // ============================================================
    // TOUCH
    // ============================================================

    fun contains(x: Float, y: Float): Boolean {

        return rect.contains(x, y)
    }

    fun press() {

        pressed = true
    }

    fun release() {

        pressed = false
    }


    // ============================================================
    // BITMAP LOADER
    // ============================================================

    private fun loadBitmap(res: Int): Bitmap {

        val drawable =
            ContextCompat.getDrawable(context, res)
                ?: throw RuntimeException("Drawable not found: $res")

        val bmp = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bmp)

        drawable.setBounds(
            0,
            0,
            canvas.width,
            canvas.height
        )

        drawable.draw(canvas)

        return bmp
    }

}