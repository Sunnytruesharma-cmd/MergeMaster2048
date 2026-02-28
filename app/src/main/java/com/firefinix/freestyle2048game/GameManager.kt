package com.firefinix.freestyle2048game

import android.graphics.Canvas
import kotlin.math.*
import kotlin.random.Random
import java.util.ArrayList
import java.util.ArrayDeque
import java.util.Collections

class GameManager(
    private val screenWidth: Int,
    private val screenHeight: Int
) {

    val COLS = 5
    val ROWS = 8

    val tileSize = screenWidth / COLS.toFloat()
    val gridSize = tileSize * COLS
    val gridHeight = tileSize * ROWS

    val gridLeft = (screenWidth - gridSize) / 2f

    private val topSafeArea = screenHeight * 0.12f
    private val buttonBarHeight = screenHeight * 0.12f
    private val adsBarHeight = screenHeight * 0.10f

    private val bottomReserved = buttonBarHeight + adsBarHeight
    private val usableHeight = screenHeight - topSafeArea - bottomReserved

    private val verticalBias = 0.18f
    val gridTop =
        topSafeArea + ((usableHeight - gridHeight).coerceAtLeast(0f)) * verticalBias

    val grid = Array(ROWS) { arrayOfNulls<Tile>(COLS) }

    // 🔥 THREAD SAFE LIST
    private val tiles = Collections.synchronizedList(ArrayList<Tile>())

    private val mergeQueue = ArrayDeque<MergeOperation>()
    private var mergeInProgress = false

    private var score: Long = 0
    private var gems: Long = 0
    private var crowns: Long = 0

    fun getScore() = score
    fun getGems() = gems
    fun getCrowns() = crowns

    private var spawnColumn = COLS / 2
    private var spawnVisualColumn = spawnColumn.toFloat()
    private val spawnFollowSpeed = 12f
    private var nextTileValue = generateTileValue()
    private var spawnLocked = false
    private var spawnerTouchActive = false

    fun getSpawnVisualColumn() = spawnVisualColumn
    fun getNextTileValue() = nextTileValue
    fun isSpawnerTouchActive() = spawnerTouchActive

    fun setSpawnerTouchActive(active: Boolean) {
        spawnerTouchActive = active
    }

    fun setSpawnColumn(col: Int) {
        if (!spawnLocked)
            spawnColumn = col.coerceIn(0, COLS - 1)
    }

    private class MergeOperation(
        val anchor: Tile,
        val sources: ArrayList<Tile>,
        val finalValue: Int
    )

    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        updateSpawner(dt)

        var allSettled = true

        // Safe snapshot iteration
        val snapshot = ArrayList(tiles)

        for (tile in snapshot) {
            tile.update(dt)
            if (!tile.isSettled()) {
                allSettled = false
            }
        }

        if (allSettled && !mergeInProgress) {
            spawnLocked = false
        }
    }

    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        // 🔥 SAFE SNAPSHOT FOR RENDER
        val snapshot = ArrayList(tiles)

        for (tile in snapshot) {
            tile.draw(canvas)
        }
    }

    // ============================================================
    // SPAWN TILE
    // ============================================================

    fun spawnTile() {

        if (spawnLocked || mergeInProgress) return

        val row = calculateLandingRow(spawnColumn)
        if (row == -1) return

        val x = gridLeft + spawnColumn * tileSize
        val startY = gridTop - tileSize
        val targetY = gridTop + row * tileSize

        val tile = Tile(
            spawnColumn,
            row,
            nextTileValue,
            tileSize,
            x,
            startY,
            targetY
        )

        synchronized(tiles) {
            tiles.add(tile)
        }

        grid[row][spawnColumn] = tile

        nextTileValue = generateTileValue()
        spawnLocked = true
    }

    private fun calculateLandingRow(col: Int): Int {
        for (r in ROWS - 1 downTo 0) {
            if (grid[r][col] == null) {
                return r
            }
        }
        return -1
    }

    private fun updateSpawner(dt: Float) {
        val delta = spawnColumn - spawnVisualColumn
        spawnVisualColumn += delta * spawnFollowSpeed * dt
    }

    private fun generateTileValue(): Int {
        return if (Random.nextFloat() < 0.85f) 2 else 4
    }
}