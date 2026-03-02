package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.min
import kotlin.random.Random

class GameManager(
    private val screenWidth: Int,
    private val screenHeight: Int
) {

    val COLS = 5
    val ROWS = 8

    val gridSize: Float
    val tileSize: Float
    val gridLeft: Float
    val gridTop: Float
    val gridBottom: Float

    private val tiles = Array(ROWS) { IntArray(COLS) }

    private var spawnColumn = 0
    private var fallingRow = -1
    private var fallingY = 0f
    private var fallingValue = 2

    private val tilePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 42f
    }

    init {
        gridSize = screenWidth * 0.9f
        tileSize = gridSize / COLS

        gridLeft = (screenWidth - gridSize) / 2f
        gridTop = 40f
        gridBottom = gridTop + tileSize * ROWS
    }

    fun update(dt: Float) {

        if (fallingRow >= 0) {
            fallingY += 900f * dt

            val targetY = gridTop + fallingRow * tileSize

            if (fallingY >= targetY) {
                tiles[fallingRow][spawnColumn] = fallingValue
                fallingRow = -1
            }
        }
    }

    fun render(canvas: Canvas) {

        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {

                val left = gridLeft + c * tileSize
                val top = gridTop + r * tileSize
                val right = left + tileSize
                val bottom = top + tileSize

                tilePaint.color = Color.parseColor("#E15BB5")
                canvas.drawRoundRect(
                    RectF(left, top, right, bottom),
                    30f,
                    30f,
                    tilePaint
                )

                val value = tiles[r][c]
                if (value != 0) {
                    canvas.drawText(
                        value.toString(),
                        left + tileSize / 2,
                        top + tileSize / 2 + 15,
                        textPaint
                    )
                }
            }
        }

        if (fallingRow >= 0) {

            val left = gridLeft + spawnColumn * tileSize
            val right = left + tileSize
            val bottom = fallingY + tileSize

            tilePaint.color = Color.parseColor("#8E5BE8")

            canvas.drawRoundRect(
                RectF(left, fallingY, right, bottom),
                30f,
                30f,
                tilePaint
            )

            canvas.drawText(
                fallingValue.toString(),
                left + tileSize / 2,
                fallingY + tileSize / 2 + 15,
                textPaint
            )
        }
    }

    fun setSpawnColumn(col: Int) {
        spawnColumn = col
    }

    fun spawnTile() {

        if (fallingRow >= 0) return

        for (r in ROWS - 1 downTo 0) {
            if (tiles[r][spawnColumn] == 0) {
                fallingRow = r
                fallingY = gridTop - tileSize
                fallingValue = if (Random.nextBoolean()) 2 else 4
                break
            }
        }
    }

    fun resetGame() {
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                tiles[r][c] = 0
            }
        }
    }

    fun useHammer() {
        // future logic
    }

    fun useShuffle() {
        // future logic
    }
}