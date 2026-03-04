package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var gameManager: GameManager
        private set

    private var lastTime = System.nanoTime()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Initialize GameManager once view has size
        gameManager = GameManager(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (::gameManager.isInitialized) {

            val now = System.nanoTime()
            val dt = (now - lastTime) / 1_000_000_000f
            lastTime = now

            gameManager.update(dt)
            gameManager.render(canvas)
        }

        invalidate() // Continuous redraw
    }

    fun resume() {
        invalidate()
    }

    fun pause() {
        // nothing required for this simple loop
    }
}