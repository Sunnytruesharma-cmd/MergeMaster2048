package com.firefinix.freestyle2048game

import android.graphics.RectF
import kotlin.math.max

class UILayoutController(

    private val screenWidth: Int,
    private val screenHeight: Int,

    private val tileSize: Float,

    private val cols: Int,
    private val rows: Int,

    private val gridLeft: Float

) {

    // ============================================================
    // SAFE SCALE UNIT
    // ============================================================

    private val unit =
        tileSize


    // ============================================================
    // ZONES
    // ============================================================

    val topZone =
        RectF()

    val spawnerZone =
        RectF()

    val gridZone =
        RectF()

    val bottomZone =
        RectF()


    // ============================================================
    // BUTTON RECTS
    // ============================================================

    val pauseButtonRect =
        RectF()

    val soundButtonRect =
        RectF()

    val undoButtonRect =
        RectF()

    val restartButtonRect =
        RectF()

    val settingsButtonRect =
        RectF()


    // ============================================================
    // PANEL RECTS
    // ============================================================

    val scorePanelRect =
        RectF()

    val bestScorePanelRect =
        RectF()


    // ============================================================
    // INITIALIZE
    // ============================================================

    init {

        calculateZones()

        calculateTopButtons()

        calculateBottomButtons()

        calculateScorePanels()
    }


    // ============================================================
    // ZONE CALCULATION
    // ============================================================

    private fun calculateZones() {

        val topZoneHeight =
            unit * 1.2f

        val spawnerZoneHeight =
            unit * 1.4f

        val gridHeight =
            rows * unit

        val bottomZoneHeight =
            unit * 1.3f


        // TOP ZONE

        topZone.set(
            0f,
            0f,
            screenWidth.toFloat(),
            topZoneHeight
        )


        // SPAWNER ZONE

        val spawnerTop =
            topZone.bottom

        spawnerZone.set(
            0f,
            spawnerTop,
            screenWidth.toFloat(),
            spawnerTop + spawnerZoneHeight
        )


        // GRID ZONE

        val gridTop =
            spawnerZone.bottom + unit * 0.2f

        gridZone.set(
            gridLeft,
            gridTop,
            gridLeft + cols * unit,
            gridTop + gridHeight
        )


        // BOTTOM ZONE

        val bottomTop =
            gridZone.bottom + unit * 0.3f

        bottomZone.set(
            0f,
            bottomTop,
            screenWidth.toFloat(),
            bottomTop + bottomZoneHeight
        )
    }


    // ============================================================
    // TOP BUTTONS
    // ============================================================

    private fun calculateTopButtons() {

        val buttonSize =
            unit * 0.65f

        val padding =
            unit * 0.25f


        // SOUND BUTTON (LEFT)

        soundButtonRect.set(

            padding,

            topZone.centerY() - buttonSize / 2,

            padding + buttonSize,

            topZone.centerY() + buttonSize / 2
        )


        // PAUSE BUTTON (RIGHT)

        pauseButtonRect.set(

            screenWidth - padding - buttonSize,

            topZone.centerY() - buttonSize / 2,

            screenWidth - padding,

            topZone.centerY() + buttonSize / 2
        )
    }


    // ============================================================
    // SCORE PANELS
    // ============================================================

    private fun calculateScorePanels() {

        val panelWidth =
            unit * 1.8f

        val panelHeight =
            unit * 0.7f

        val spacing =
            unit * 0.25f


        val totalWidth =
            panelWidth * 2 + spacing


        val startX =
            (screenWidth - totalWidth) / 2f


        val y =
            topZone.centerY() - panelHeight / 2


        scorePanelRect.set(
            startX,
            y,
            startX + panelWidth,
            y + panelHeight
        )


        bestScorePanelRect.set(
            scorePanelRect.right + spacing,
            y,
            scorePanelRect.right + spacing + panelWidth,
            y + panelHeight
        )
    }


    // ============================================================
    // BOTTOM BUTTONS
    // ============================================================

    private fun calculateBottomButtons() {

        val buttonSize =
            unit * 0.9f

        val spacing =
            unit * 0.3f


        val totalWidth =
            buttonSize * 3 + spacing * 2


        val startX =
            (screenWidth - totalWidth) / 2f


        val centerY =
            bottomZone.centerY()


        // UNDO

        undoButtonRect.set(
            startX,
            centerY - buttonSize / 2,
            startX + buttonSize,
            centerY + buttonSize / 2
        )


        // RESTART

        restartButtonRect.set(
            undoButtonRect.right + spacing,
            centerY - buttonSize / 2,
            undoButtonRect.right + spacing + buttonSize,
            centerY + buttonSize / 2
        )


        // SETTINGS

        settingsButtonRect.set(
            restartButtonRect.right + spacing,
            centerY - buttonSize / 2,
            restartButtonRect.right + spacing + buttonSize,
            centerY + buttonSize / 2
        )
    }


    // ============================================================
    // SPAWNER POSITION
    // ============================================================

    fun getSpawnerCenterY(): Float {

        return spawnerZone.centerY()
    }


    fun getSpawnerShadowY(): Float {

        return spawnerZone.centerY() + unit * 0.45f
    }


    // ============================================================
    // GRID HELPERS
    // ============================================================

    fun getGridTop(): Float {

        return gridZone.top
    }


    fun getGridBottom(): Float {

        return gridZone.bottom
    }


    fun getGridCenterX(): Float {

        return gridZone.centerX()
    }


    // ============================================================
    // SAFE AREA CHECKS
    // ============================================================

    fun isInsideGrid(x: Float, y: Float): Boolean {

        return gridZone.contains(x, y)
    }


    fun isInsideTopZone(x: Float, y: Float): Boolean {

        return topZone.contains(x, y)
    }


    fun isInsideBottomZone(x: Float, y: Float): Boolean {

        return bottomZone.contains(x, y)
    }

}
