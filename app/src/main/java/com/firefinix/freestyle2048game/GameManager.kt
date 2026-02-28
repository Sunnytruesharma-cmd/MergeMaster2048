package com.firefinix.freestyle2048game

import android.graphics.Canvas
import kotlin.random.Random
import java.util.ArrayList

class GameManager(
    private val screenWidth: Int,
    private val screenHeight: Int
) {

    // =============================
    // GRID CONFIG
    // =============================

    val COLS = 5
    val ROWS = 8

    // =============================
// UI RESERVED AREAS
// =============================

    // Top HUD for score, crown, next tile
    private val topHudHeight = screenHeight * 0.16f

    // Bottom buttons + ads
    private val buttonBarHeight = screenHeight * 0.12f
    private val adsBarHeight = screenHeight * 0.10f
    private val bottomReserved = buttonBarHeight + adsBarHeight

    // Usable space for grid only
    private val usableHeight =
        screenHeight - topHudHeight - bottomReserved

    // =============================
    // GRID SIZE CONTROL
    // =============================

    private val gridWidthRatio = 0.90f
    private val effectiveGridWidth =
        screenWidth * gridWidthRatio

    private val tileGapRatio = 0.10f
    private val totalGapWidth =
        effectiveGridWidth * tileGapRatio

    val tileGap: Float =
        totalGapWidth / (COLS - 1)

    val tileSize: Float =
        (effectiveGridWidth - totalGapWidth) / COLS

    val gridSize: Float =
        effectiveGridWidth

    val gridHeight: Float =
        tileSize * ROWS + tileGap * (ROWS - 1)

    val gridLeft: Float =
        (screenWidth - gridSize) / 2f

    private val downwardOffset = screenHeight * 0.05f

    val gridTop: Float =
        topHudHeight +
                (usableHeight - gridHeight) / 2f

    val gridBottom: Float
        get() = gridTop + gridHeight

    // =============================
    // GRID STORAGE
    // =============================

    val grid = Array(ROWS) { arrayOfNulls<Tile>(COLS) }
    private val tiles = ArrayList<Tile>()

    // =============================
    // SCORE / ECONOMY (RESTORED)
    // =============================

    private var score: Long = 0
    private var gems: Long = 0
    private var crowns: Long = 0

    fun getScore(): Long = score
    fun getGems(): Long = gems
    fun getCrowns(): Long = crowns

    // =============================
    // SPAWN SYSTEM (UNCHANGED)
    // =============================

    private var spawnColumn = COLS / 2
    private var nextTileValue = generateTileValue()

    fun setSpawnColumn(col: Int) {
        spawnColumn = col.coerceIn(0, COLS - 1)
    }

    fun getNextTileValue(): Int = nextTileValue

    // =============================
    // UPDATE / RENDER
    // =============================

    fun update(dt: Float) {
        val snapshot = ArrayList(tiles)
        for (tile in snapshot) {
            tile.update(dt)
        }
    }

    fun render(canvas: Canvas) {
        val snapshot = ArrayList(tiles)
        for (tile in snapshot) {
            tile.draw(canvas)
        }
    }

    // =============================
    // SPAWN TILE
    // =============================

    fun spawnTile() {

        val row = calculateLandingRow(spawnColumn)
        if (row == -1) return

        val x =
            gridLeft +
                    spawnColumn * (tileSize + tileGap)

        val startY = gridTop - tileSize

        val targetY =
            gridTop +
                    row * (tileSize + tileGap)

        val tile = Tile(
            spawnColumn,
            row,
            nextTileValue,
            tileSize,
            x,
            startY,
            targetY
        )

        tiles.add(tile)
        grid[row][spawnColumn] = tile

        nextTileValue = generateTileValue()
    }

    private fun calculateLandingRow(col: Int): Int {
        for (r in ROWS - 1 downTo 0) {
            if (grid[r][col] == null) {
                return r
            }
        }
        return -1
    }

    private fun generateTileValue(): Int {
        return if (Random.nextFloat() < 0.85f) 2 else 4
    }
}