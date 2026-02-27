package com.firefinix.freestyle2048game

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.max

class Particle {

    // ============================================================
    // STATE
    // ============================================================

    var active = false

    private var x = 0f
    private var y = 0f

    private var vx = 0f
    private var vy = 0f

    private var size = 0f

    private var life = 0f
    private var maxLife = 0f

    private var color = 0


    // ============================================================
    // ORIGINAL INIT (UNCHANGED)
    // ============================================================

    fun init(
        startX: Float,
        startY: Float,
        color: Int
    ) {

        active = true

        x = startX
        y = startY

        this.color = color

        vx = (Math.random().toFloat() - 0.5f) * 240f
        vy = (Math.random().toFloat() - 0.5f) * 240f

        size = Math.random().toFloat() * 8f + 4f

        life = Math.random().toFloat() * 0.4f + 0.3f

        maxLife = life
    }


    // ============================================================
    // NEW BURST INIT (SAFE ADDITION)
    // ============================================================

    fun initBurst(
        startX: Float,
        startY: Float,
        velocityX: Float,
        velocityY: Float,
        startSize: Float,
        startLife: Float,
        color: Int
    ) {

        active = true

        x = startX
        y = startY

        vx = velocityX
        vy = velocityY

        size = startSize

        life = startLife
        maxLife = startLife

        this.color = color
    }


    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        if (!active)
            return

        life -= dt

        if (life <= 0f) {

            active = false
            return
        }

        x += vx * dt
        y += vy * dt

        // smooth damping
        vx *= 0.92f
        vy *= 0.92f
    }


    // ============================================================
    // DRAW
    // ============================================================

    fun draw(canvas: Canvas, paint: Paint) {

        if (!active)
            return

        val alpha =
            ((life / maxLife) * 255f).toInt()

        paint.color = color

        paint.alpha = max(0, alpha)

        canvas.drawCircle(
            x,
            y,
            size,
            paint
        )
    }

}
