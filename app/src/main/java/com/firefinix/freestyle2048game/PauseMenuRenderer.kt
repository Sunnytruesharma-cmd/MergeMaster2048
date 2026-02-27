package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.sin

class PauseMenuRenderer(

    private val screenWidth: Int,
    private val screenHeight: Int,
    private val tileSize: Float

) {

    // ============================================================
    // STATE
    // ============================================================

    private var visible = false

    private var animTime = 0f

    private val animSpeed = 6f


    // ============================================================
    // PANEL RECT
    // ============================================================

    private val panelRect = RectF()

    private val resumeButtonRect = RectF()

    private val restartButtonRect = RectF()

    private val settingsButtonRect = RectF()


    // ============================================================
    // PAINTS
    // ============================================================

    private val dimPaint =
        Paint().apply {
            color = Color.argb(160, 0, 0, 0)
        }

    private val panelPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.HSVToColor(
                floatArrayOf(220f, 0.18f, 0.18f)
            )
        }

    private val borderPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            style = Paint.Style.STROKE

            strokeWidth = tileSize * 0.06f

            color = Color.argb(90,255,255,255)
        }

    private val titlePaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize = tileSize * 0.8f

            isFakeBoldText = true
        }

    private val buttonPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.HSVToColor(
                floatArrayOf(220f, 0.22f, 0.26f)
            )
        }

    private val buttonTextPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            textSize = tileSize * 0.45f

            isFakeBoldText = true
        }


    // ============================================================
    // INITIALIZE LAYOUT
    // ============================================================

    init {

        calculateLayout()
    }


    private fun calculateLayout() {

        val panelWidth =
            tileSize * 4.2f

        val panelHeight =
            tileSize * 5.5f

        val cx =
            screenWidth / 2f

        val cy =
            screenHeight / 2f


        panelRect.set(
            cx - panelWidth / 2,
            cy - panelHeight / 2,
            cx + panelWidth / 2,
            cy + panelHeight / 2
        )


        val buttonWidth =
            panelWidth * 0.7f

        val buttonHeight =
            tileSize * 0.9f

        val spacing =
            tileSize * 0.4f


        val startY =
            panelRect.centerY()


        resumeButtonRect.set(
            cx - buttonWidth / 2,
            startY - buttonHeight,
            cx + buttonWidth / 2,
            startY
        )

        restartButtonRect.set(
            cx - buttonWidth / 2,
            startY + spacing,
            cx + buttonWidth / 2,
            startY + spacing + buttonHeight
        )

        settingsButtonRect.set(
            cx - buttonWidth / 2,
            startY + spacing * 2 + buttonHeight,
            cx + buttonWidth / 2,
            startY + spacing * 2 + buttonHeight * 2
        )
    }


    // ============================================================
    // CONTROL
    // ============================================================

    fun show() {

        visible = true
    }

    fun hide() {

        visible = false
    }

    fun isVisible(): Boolean {

        return visible
    }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        if (!visible) return

        animTime += dt * animSpeed
    }


    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        if (!visible) return


        // Background dim

        canvas.drawRect(
            0f,
            0f,
            screenWidth.toFloat(),
            screenHeight.toFloat(),
            dimPaint
        )


        // Panel animation

        val scale =
            0.95f + 0.05f * sin(animTime)

        canvas.save()

        canvas.scale(
            scale,
            scale,
            panelRect.centerX(),
            panelRect.centerY()
        )


        // Panel

        canvas.drawRoundRect(
            panelRect,
            tileSize * 0.3f,
            tileSize * 0.3f,
            panelPaint
        )


        canvas.drawRoundRect(
            panelRect,
            tileSize * 0.3f,
            tileSize * 0.3f,
            borderPaint
        )


        // Title

        val titleY =
            panelRect.top + tileSize * 1.2f

        canvas.drawText(
            "PAUSED",
            panelRect.centerX(),
            titleY,
            titlePaint
        )


        // Buttons

        drawButton(canvas, resumeButtonRect, "RESUME")

        drawButton(canvas, restartButtonRect, "RESTART")

        drawButton(canvas, settingsButtonRect, "SETTINGS")


        canvas.restore()
    }


    // ============================================================
    // BUTTON DRAW
    // ============================================================

    private fun drawButton(
        canvas: Canvas,
        rect: RectF,
        text: String
    ) {

        canvas.drawRoundRect(
            rect,
            tileSize * 0.25f,
            tileSize * 0.25f,
            buttonPaint
        )

        canvas.drawRoundRect(
            rect,
            tileSize * 0.25f,
            tileSize * 0.25f,
            borderPaint
        )


        val textY =
            rect.centerY() -
                    (buttonTextPaint.descent() +
                            buttonTextPaint.ascent()) / 2

        canvas.drawText(
            text,
            rect.centerX(),
            textY,
            buttonTextPaint
        )
    }


    // ============================================================
    // INPUT CHECK
    // ============================================================

    fun checkButtonClick(x: Float, y: Float): PauseButton? {

        if (!visible) return null

        return when {

            resumeButtonRect.contains(x, y) ->
                PauseButton.RESUME

            restartButtonRect.contains(x, y) ->
                PauseButton.RESTART

            settingsButtonRect.contains(x, y) ->
                PauseButton.SETTINGS

            else -> null
        }
    }

}
