package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.concurrent.thread

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var gameManager: GameManager? = null

    @Volatile
    private var running = false

    private var gameThread: Thread? = null
    private var lastTime = System.nanoTime()

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    // ================= SURFACE CREATED =================

    override fun surfaceCreated(holder: SurfaceHolder) {

        gameManager = GameManager(width, height)

        running = true
        startLoop()
    }

    // ================= GAME LOOP =================

    private fun startLoop() {

        gameThread = thread(start = true) {

            while (running) {

                val gm = gameManager ?: continue   // 🔥 SAFETY CHECK

                val now = System.nanoTime()
                val dt = (now - lastTime) / 1_000_000_000f
                lastTime = now

                gm.update(dt)

                val canvas: Canvas? = holder.lockCanvas()
                if (canvas != null) {
                    synchronized(holder) {
                        gm.render(canvas)
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        running = false
        gameThread?.join()
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {}

    // ================= LIFECYCLE =================

    fun resume() {
        if (!running && gameManager != null) {
            running = true
            startLoop()
        }
    }

    fun pause() {
        running = false
    }

    // ================= BUTTON CALLBACKS =================

    fun onHammerPressed() {
        gameManager?.onHammer()
    }

    fun onUndoPressed() {
        gameManager?.onUndo()
    }

    fun onPausePressed() {
        gameManager?.togglePause()
    }
}