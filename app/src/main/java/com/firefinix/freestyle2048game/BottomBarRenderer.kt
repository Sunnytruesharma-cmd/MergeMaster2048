package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF

class BottomBarRenderer(private val context: Context) {

    // ============================================================
    // BITMAPS
    // ============================================================

    private val swapBitmap: Bitmap =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_swap_tiles
        )

    // Future buttons
    private var hammerBitmap: Bitmap? = null
    private var undoBitmap: Bitmap? = null
    private var shopBitmap: Bitmap? = null
    private var settingsBitmap: Bitmap? = null

    // ============================================================
    // POSITION
    // ============================================================

    private val swapRect = RectF()

    private var barHeight = 0f
    private var screenWidth = 0f
    private var screenHeight = 0f

    // ============================================================
    // INIT
    // ============================================================

    fun onSurfaceChanged(width: Int, height: Int) {

        screenWidth = width.toFloat()
        screenHeight = height.toFloat()

        barHeight = height * 0.14f

        val buttonSize = barHeight * 0.65f

        val centerX = screenWidth * 0.5f
        val centerY = screenHeight - barHeight / 2f

        swapRect.set(
            centerX - buttonSize / 2f,
            centerY - buttonSize / 2f,
            centerX + buttonSize / 2f,
            centerY + buttonSize / 2f
        )
    }

    // ============================================================
    // DRAW
    // ============================================================

    fun draw(canvas: Canvas) {

        drawSwapButton(canvas)
    }

    private fun drawSwapButton(canvas: Canvas) {

        canvas.drawBitmap(
            swapBitmap,
            null,
            swapRect,
            null
        )
    }

    // ============================================================
    // INPUT DETECTION
    // ============================================================

    fun isSwapClicked(x: Float, y: Float): Boolean {

        return swapRect.contains(x, y)
    }
}