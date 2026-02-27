package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat

/**
 * HeartRenderer
 *
 * Production-grade renderer for Hearts currency icon.
 * Optimized with bitmap caching for 60 FPS performance.
 *
 * Compatible with:
 * PremiumCapsuleButton
 * TopBarRenderer
 * Popup systems
 */
object HeartRenderer {

    // ============================================================
    // BITMAP CACHE
    // ============================================================

    private var heartBitmap: Bitmap? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        isFilterBitmap = true
        isDither = true
    }


    // ============================================================
    // INITIALIZATION
    // ============================================================

    fun init(context: Context) {

        if (heartBitmap != null)
            return

        heartBitmap = loadBitmap(
            context,
            R.drawable.ic_heart
        )
    }


    // ============================================================
    // RENDER FUNCTION
    // ============================================================

    fun render(
        canvas: Canvas,
        x: Float,
        y: Float,
        size: Float
    ) {

        val bmp = heartBitmap ?: return

        val half = size * 0.5f

        val rect = RectF(
            x - half,
            y - half,
            x + half,
            y + half
        )

        canvas.drawBitmap(
            bmp,
            null,
            rect,
            paint
        )
    }


    // ============================================================
    // INTERNAL BITMAP LOADER
    // ============================================================

    private fun loadBitmap(
        context: Context,
        resId: Int
    ): Bitmap {

        val drawable =
            ContextCompat.getDrawable(context, resId)
                ?: throw RuntimeException(
                    "Heart drawable not found"
                )

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