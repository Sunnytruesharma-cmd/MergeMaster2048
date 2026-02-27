package com.firefinix.freestyle2048game

import android.graphics.*
import kotlin.math.*
import kotlin.random.Random

class ParticleSystem {

    private val particles = mutableListOf<Particle>()

    // ============================================================
    // SPAWN MERGE PARTICLES (DOPAMINE MODE)
    // ============================================================

    fun spawnMergeParticles(
        x: Float,
        y: Float,
        color: Int,
        value: Int,
        combo: Int
    ) {

        val intensity = log2(value.toFloat()).toInt()

        // Base particle count by tile value
        val base = when {
            intensity <= 3 -> 12
            intensity <= 6 -> 24
            intensity <= 9 -> 40
            else -> 70
        }

        // Chain escalation
        val chainBonus = combo * 8

        // Final count with safety cap
        val count = (base + chainBonus).coerceAtMost(140)

        // Dynamic scaling
        val speedMultiplier = 1f + intensity * 0.12f + combo * 0.1f
        val sizeMultiplier = 1f + intensity * 0.08f + combo * 0.12f
        val lifeMultiplier = 1f + intensity * 0.08f + combo * 0.08f

        // Boosted color based on combo
        val boostedColor = boostColorIntensity(color, combo)

        repeat(count) {

            val angle = Random.nextFloat() * 2f * PI.toFloat()
            val speed = Random.nextFloat() * 260f * speedMultiplier

            val vx = cos(angle) * speed
            val vy = sin(angle) * speed

            particles.add(
                Particle(
                    x,
                    y,
                    vx,
                    vy,
                    boostedColor,
                    4f * sizeMultiplier,
                    0.6f * lifeMultiplier
                )
            )
        }
    }

    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {

        val iterator = particles.iterator()

        while (iterator.hasNext()) {

            val p = iterator.next()
            p.update(dt)

            if (p.isDead())
                iterator.remove()
        }
    }

    // ============================================================
    // RENDER
    // ============================================================

    fun render(canvas: Canvas) {

        for (p in particles)
            p.draw(canvas)
    }

    // ============================================================
    // PARTICLE CLASS
    // ============================================================

    private class Particle(
        var x: Float,
        var y: Float,
        private var vx: Float,
        private var vy: Float,
        color: Int,
        private val size: Float,
        private val life: Float
    ) {

        private var time = 0f

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
        }

        fun update(dt: Float) {

            time += dt

            x += vx * dt
            y += vy * dt

            // Friction
            vx *= 0.95f
            vy *= 0.95f

            // Fade out
            val alpha = ((1f - time / life) * 255).toInt()
            paint.alpha = alpha.coerceAtLeast(0)
        }

        fun draw(canvas: Canvas) {
            canvas.drawCircle(x, y, size, paint)
        }

        fun isDead(): Boolean {
            return time >= life
        }
    }

    // ============================================================
    // COLOR BOOST SYSTEM
    // ============================================================

    private fun boostColorIntensity(color: Int, combo: Int): Int {

        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        // Brightness boost
        hsv[2] = (hsv[2] + combo * 0.05f).coerceAtMost(1f)

        // Saturation boost
        hsv[1] = (hsv[1] + combo * 0.04f).coerceAtMost(1f)

        return Color.HSVToColor(hsv)
    }

    private fun log2(value: Float): Float {
        return ln(value) / ln(2f)
    }
}