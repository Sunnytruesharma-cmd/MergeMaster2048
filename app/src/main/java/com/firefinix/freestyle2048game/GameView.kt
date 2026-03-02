package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.concurrent.thread

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    lateinit var gameManager: GameManager
        private set

    private var gameThread: Thread? = null
    @Volatile private var running = false
    private var lastTime = 0L

    init {
        holder.addCallback(this)
        isFocusable = true
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameManager = GameManager(width, height)
        startLoop()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopLoop()
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {}

    private fun startLoop() {
        if (running) return
        running = true
        lastTime = System.nanoTime()

        gameThread = thread(start = true) {
            while (running) {
                val now = System.nanoTime()
                val dt = (now - lastTime) / 1_000_000_000f
                lastTime = now
                update(dt)
                render()
            }
        }
    }

    private fun stopLoop() {
        running = false
        gameThread?.join()
        gameThread = null
    }

    private fun update(dt: Float) {
        gameManager.update(dt)
    }

    private fun render() {
        val canvas = holder.lockCanvas() ?: return
        try {
            canvas.drawColor(Color.parseColor("#0B0B14"))
            gameManager.render(canvas)
        } finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                val col = ((event.x - gameManager.gridLeft) /
                        gameManager.tileSize).toInt()
                    .coerceIn(0, gameManager.COLS - 1)

                gameManager.setSpawnColumn(col)
            }

            MotionEvent.ACTION_UP -> {
                gameManager.spawnTile()
            }
        }

        return true
    }

    fun resume() {
        if (holder.surface.isValid) startLoop()
    }

    fun pause() {
        stopLoop()
    }
}