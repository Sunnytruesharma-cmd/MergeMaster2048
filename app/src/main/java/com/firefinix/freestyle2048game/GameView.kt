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

    @Volatile
    private var running = false

    private var lastTime = 0L

    init {
        holder.addCallback(this)
        isFocusable = true
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    // ============================================================
    // SURFACE CALLBACKS
    // ============================================================

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (width == 0 || height == 0) return
        gameManager = GameManager(width, height)
        startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopLoop()
    }

    // ============================================================
    // GAME LOOP
    // ============================================================

    private fun startLoop() {
        if (running) return

        running = true
        lastTime = System.nanoTime()

        gameThread = thread(start = true) {
            while (running) {

                val now = System.nanoTime()
                val dt = (now - lastTime) / 1_000_000_000f
                lastTime = now

                synchronized(this) {
                    update(dt)
                    render()
                }
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
            drawPremiumBackground(canvas)
            gameManager?.render(canvas)
        } finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // ============================================================
    // BACKGROUND (Soft Matte Grid)
    // ============================================================

    private fun drawPremiumBackground(canvas: Canvas) {

        val w = width.toFloat()
        val h = height.toFloat()

        // Base dark
        canvas.drawColor(Color.parseColor("#0B0B14"))

        val gm = gameManager ?: return

        val columnWidth = gm.gridSize / gm.COLS
        val gridTop = gm.gridTop
        val gridBottom = gm.gridTop + gm.ROWS * gm.tileSize

        canvas.save()

        canvas.clipRect(
            gm.gridLeft,
            gridTop,
            gm.gridLeft + gm.gridSize,
            gridBottom
        )

        for (i in 0 until gm.COLS) {

            val left = gm.gridLeft + i * columnWidth
            val right = left + columnWidth

            val baseColor = if (i % 2 == 0)
                "#14141C"   // darker
            else
                "#23232B"   // slightly lighter

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.parseColor(baseColor)
            paint.alpha = 140   // important increase

            canvas.drawRect(
                left,
                gridTop,
                right,
                gridBottom,
                paint
            )
        }

        canvas.restore()

        // Soft vignette
        val vignette = RadialGradient(
            w / 2f,
            h / 2f,
            h,
            intArrayOf(
                Color.TRANSPARENT,
                Color.parseColor("#AA000000")
            ),
            floatArrayOf(0.7f, 1f),
            Shader.TileMode.CLAMP
        )

        val vignettePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        vignettePaint.shader = vignette

        canvas.drawRect(0f, 0f, w, h, vignettePaint)
    }

    // ============================================================
    // TOUCH
    // ============================================================

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val gm = gameManager ?: return true

        when (event.action) {

            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                val col = ((event.x - gm.gridLeft) / gm.tileSize).toInt()
                gm.setSpawnColumn(col)
                gm.setSpawnerTouchActive(true)
            }

            MotionEvent.ACTION_UP -> {
                gm.spawnTile()
                gm.setSpawnerTouchActive(false)
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