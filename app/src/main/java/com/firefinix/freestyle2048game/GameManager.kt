package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

class GameManager(
    private val screenWidth: Int,
    private val screenHeight: Int
) {

    private val COLS = 5
    private val ROWS = 8

    private val tileSize = screenWidth / COLS.toFloat()

    private val paint = Paint()

    private var paused = false

    private var score: Long = 0
    private var gems: Long = 0
    private var crowns: Long = 0

    private val grid = Array(ROWS) { IntArray(COLS) }

    init {
        spawnRandomTile()
    }

    // ================= UPDATE =================

    fun update(dt: Float) {
        if (paused) return
    }

    // ================= RENDER =================

    fun render(canvas: Canvas) {

        canvas.drawColor(Color.parseColor("#101014"))

        for (row in 0 until ROWS) {
            for (col in 0 until COLS) {

                val value = grid[row][col]

                val x = col * tileSize
                val y = row * tileSize

                paint.color = if (value == 0)
                    Color.DKGRAY
                else
                    Color.parseColor("#FF6FAE")

                canvas.drawRect(
                    x,
                    y,
                    x + tileSize,
                    y + tileSize,
                    paint
                )
            }
        }
    }

    private fun spawnRandomTile() {
        val r = Random.nextInt(ROWS)
        val c = Random.nextInt(COLS)
        grid[r][c] = 2
        score += 2
    }

    // ================= GETTERS (FIX FOR GameController) =================

    fun getScore(): Long {
        return score
    }

    fun getGems(): Long {
        return gems
    }

    fun getCrowns(): Long {
        return crowns
    }

    // ================= BUTTON ACTIONS =================

    fun onHammer() {}
    fun onUndo() {}
    fun togglePause() {
        paused = !paused
    }
}