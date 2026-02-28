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

    private var gameManager: GameManager? = null
    private var gameThread: Thread? = null
    @Volatile private var running = false
    private var lastTime = 0L

    private val bottomFadePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        holder.addCallback(this)
        isFocusable = true
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (width == 0 || height == 0) return
        gameManager = GameManager(width, height)
        startLoop()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

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
        gameManager?.update(dt)
    }

    private fun render() {
        val canvas = holder.lockCanvas() ?: return
        try {
            drawBackground(canvas)

            val gm = gameManager ?: return

            canvas.save()

            canvas.clipRect(
                gm.gridLeft,
                gm.gridTop,
                gm.gridLeft + gm.gridSize,
                gm.gridTop + gm.gridHeight
            )

            gm.render(canvas)

            drawBottomFade(canvas, gm)

            canvas.restore()

        } finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // 🔥 Clean Background (No Vertical Lines)
    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#0B0B14"))
    }

    // Optional Bottom Fade (keep or remove)
    private fun drawBottomFade(canvas: Canvas, gm: GameManager) {

        val fadeHeight = gm.tileSize * 2.5f

        val bottomFade = LinearGradient(
            0f,
            gm.gridBottom - fadeHeight,
            0f,
            gm.gridBottom,
            Color.TRANSPARENT,
            Color.parseColor("#22000000"),
            Shader.TileMode.CLAMP
        )

        bottomFadePaint.shader = bottomFade

        canvas.drawRect(
            gm.gridLeft,
            gm.gridBottom - fadeHeight,
            gm.gridLeft + gm.gridSize,
            gm.gridBottom,
            bottomFadePaint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val gm = gameManager ?: return true

        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                val rawCol = ((event.x - gm.gridLeft) / gm.tileSize).toInt()
                val col = rawCol.coerceIn(0, gm.COLS - 1)
                gm.setSpawnColumn(col)
            }
            MotionEvent.ACTION_UP -> gm.spawnTile()
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