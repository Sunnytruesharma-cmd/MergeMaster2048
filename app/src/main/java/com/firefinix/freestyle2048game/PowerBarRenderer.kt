package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class PowerBarRenderer(

    private val context: Context,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val tileSize: Float

) {

    // ============================================================
    // BAR GEOMETRY
    // ============================================================

    private val barHeight =
        tileSize * 1.4f

    private val adsHeight =
        tileSize * 0.6f

    private val barTop =
        screenHeight - adsHeight - barHeight

    private val centerY =
        barTop + barHeight * 0.5f


    // ============================================================
    // BUTTON SIZE
    // ============================================================

    private val buttonSize =
        tileSize * 0.9f

    private val pauseRadius =
        tileSize * 0.45f


    // ============================================================
    // BUTTON POSITIONS
    // ============================================================

    private val spacing =
        screenWidth / 5f

    private val hammerX =
        spacing * 1f

    private val swapX =
        spacing * 2f

    private val pauseX =
        spacing * 3f

    private val undoX =
        spacing * 4f


    // ============================================================
    // BUTTON INSTANCES
    // ============================================================

    private val hammerButton =
        PremiumSquareTileButton(
            context,
            hammerX,
            centerY,
            buttonSize,
            PremiumSquareTileButton.ButtonType.HAMMER
        )


    private val swapButton =
        PremiumSquareTileButton(
            context,
            swapX,
            centerY,
            buttonSize,
            PremiumSquareTileButton.ButtonType.SWAP
        )


    private val undoButton =
        PremiumSquareTileButton(
            context,
            undoX,
            centerY,
            buttonSize,
            PremiumSquareTileButton.ButtonType.UNDO
        )


    private val pauseButton =
        PremiumIconButton(
            pauseX,
            centerY,
            pauseRadius,
            PremiumIconButton.IconType.PAUSE
        )


    // ============================================================
    // BACKGROUND PAINT
    // ============================================================

    private val backgroundPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.argb(140, 10, 10, 20)
        }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        hammerButton.update(dt)

        swapButton.update(dt)

        undoButton.update(dt)

        pauseButton.update(dt)
    }


    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        canvas.drawRect(
            0f,
            barTop,
            screenWidth.toFloat(),
            barTop + barHeight,
            backgroundPaint
        )

        hammerButton.render(canvas)

        swapButton.render(canvas)

        pauseButton.render(canvas)

        undoButton.render(canvas)
    }


    // ============================================================
    // TOUCH DETECTION
    // ============================================================

    fun isHammerPressed(x: Float, y: Float): Boolean {

        return hammerButton.contains(x, y)
    }


    fun isSwapPressed(x: Float, y: Float): Boolean {

        return swapButton.contains(x, y)
    }


    fun isPausePressed(x: Float, y: Float): Boolean {

        return pauseButton.contains(x, y)
    }


    fun isUndoPressed(x: Float, y: Float): Boolean {

        return undoButton.contains(x, y)
    }


    // ============================================================
    // PRESS / RELEASE
    // ============================================================

    fun pressHammer() {

        hammerButton.press()
    }


    fun releaseHammer() {

        hammerButton.release()
    }


    fun pressSwap() {

        swapButton.press()
    }


    fun releaseSwap() {

        swapButton.release()
    }


    fun pressPause() {

        pauseButton.press()
    }


    fun releasePause() {

        pauseButton.release()
    }


    fun pressUndo() {

        undoButton.press()
    }


    fun releaseUndo() {

        undoButton.release()
    }


    // ============================================================
    // ACCESSORS
    // ============================================================

    fun getTop(): Float {

        return barTop
    }


    fun getHeight(): Float {

        return barHeight
    }

}