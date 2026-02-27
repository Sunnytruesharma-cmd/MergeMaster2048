package com.firefinix.freestyle2048game

import android.graphics.*
import androidx.core.graphics.withSave
import kotlin.math.*
import android.graphics.BlurMaskFilter

class Tile(
    var column: Int,
    var row: Int,
    var value: Int,
    private val size: Float,
    startX: Float,
    startY: Float,
    targetYInit: Float
) {

    // ============================================================
    // POSITION
    // ============================================================

    var drawX = startX
        private set

    var drawY = startY
        private set

    private var startAnimX = startX
    private var startAnimY = startY

    private var targetX = startX
    private var targetY = targetYInit

    private var animTime = 0f
    private var animDuration = 0.14f
    private var moving = true

    // ============================================================
    // POP ANIMATION
    // ============================================================

    private var popTime = 0f
    private var popping = false
    private val popDuration = 0.12f

    // ============================================================
// ELASTIC MERGE ANIMATION (CHAIN POWER BASED)
// ============================================================

    private var mergeScaleBoost = 0f
    private var mergeAnimTime = 0f
    private var mergeAnimating = false
    private val mergeDuration = 0.18f

    fun startMergeAnimation(power: Float) {
        mergeScaleBoost = 0.15f + power * 0.05f
        mergeAnimTime = 0f
        mergeAnimating = true
    }

    // ============================================================
    // PAINTS (M2 PREMIUM SYSTEM)
    // ============================================================

    private val basePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    // ============================================================
    // PREMIUM COLOR PALETTE
    // ============================================================

    private val premiumPalette = listOf(
        "#FF4FA3", "#FF6EC7", "#9B5DE5", "#5F0F40",
        "#3A86FF", "#00B4D8", "#06D6A0", "#F4D35E",
        "#EE964B", "#F95738", "#7209B7", "#B5179E",
        "#4361EE", "#2EC4B6", "#FFD166", "#EF476F"
    )

    private fun generatePremiumColors(): Pair<Int, Int> {

        val level = (ln(value.toFloat()) / ln(2f)).toInt()
        val index = level % premiumPalette.size

        val baseColor = Color.parseColor(premiumPalette[index])

        val hsv = FloatArray(3)
        Color.colorToHSV(baseColor, hsv)

        val lightHSV = hsv.clone()
        lightHSV[2] = (hsv[2] + 0.15f).coerceAtMost(1f)

        val darkHSV = hsv.clone()
        darkHSV[2] = (hsv[2] - 0.25f).coerceAtLeast(0f)

        return Pair(
            Color.HSVToColor(lightHSV),
            Color.HSVToColor(darkHSV)
        )
    }

    // ============================================================
    // UPDATE
    // ============================================================

    fun update(dt: Float) {
        updateMovement(dt)
        updatePop(dt)
    }

    private fun updateMovement(dt: Float) {

        if (!moving) return

        animTime += dt
        var t = animTime / animDuration

        if (t >= 1f) {
            t = 1f
            moving = false
        }

        val eased = easeInOutCubic(t)

        drawX = lerp(startAnimX, targetX, eased)
        drawY = lerp(startAnimY, targetY, eased)
    }

    private fun updatePop(dt: Float) {

        if (!popping) return

        popTime += dt

        if (popTime >= popDuration) {
            popTime = popDuration
            popping = false
        }
    }

    fun setTargetPosition(x: Float, y: Float) {
        startAnimX = drawX
        startAnimY = drawY
        targetX = x
        targetY = y
        animTime = 0f
        moving = true
    }

    fun startPopAnimation() {
        popTime = 0f
        popping = true
    }

    fun isSettled(): Boolean = !moving && !popping

    // ============================================================
    // DRAW
    // ============================================================

    fun draw(canvas: Canvas) {

        val scale =
            if (popping)
                1f + 0.18f * (1f - popTime / popDuration)
            else
                1f

        val scaledSize = size * scale
        val offset = (scaledSize - size) / 2f

        val rect = RectF(
            drawX - offset,
            drawY - offset,
            drawX + size + offset,
            drawY + size + offset
        )

        drawTileBody(canvas, rect)
        drawValue(canvas, rect)
    }

    private fun drawTileBody(canvas: Canvas, rect: RectF) {

        val (lightColor, darkColor) = generatePremiumColors()

        // 1️⃣ Base Gradient
        basePaint.shader = LinearGradient(
            rect.left,
            rect.top,
            rect.right,
            rect.bottom,
            lightColor,
            darkColor,
            Shader.TileMode.CLAMP
        )

        canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, basePaint)
        basePaint.shader = null

        // 2️⃣ Top Shine
        shinePaint.shader = LinearGradient(
            rect.left,
            rect.top,
            rect.left,
            rect.centerY(),
            Color.argb(120, 255, 255, 255),
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )

        canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, shinePaint)
        shinePaint.shader = null

        // 3️⃣ Inner Shadow
        shadowPaint.color = Color.argb(60, 0, 0, 0)
        shadowPaint.strokeWidth = size * 0.05f

        canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, shadowPaint)

        // 4️⃣ Glow for higher tiles
        if (value >= 128) {

            glowPaint.color = Color.argb(
                80,
                Color.red(lightColor),
                Color.green(lightColor),
                Color.blue(lightColor)
            )

            canvas.drawRoundRect(rect, size * 0.22f, size * 0.22f, glowPaint)
        }
    }

    private fun drawValue(canvas: Canvas, rect: RectF) {

        textPaint.textSize = size * 0.38f

        textPaint.color =
            if (value <= 8)
                Color.parseColor("#3A2A00")
            else
                Color.WHITE

        val cx = rect.centerX()
        val cy = rect.centerY() -
                (textPaint.descent() + textPaint.ascent()) / 2

        canvas.drawText(
            value.toString(),
            cx,
            cy,
            textPaint
        )
    }

    // ============================================================
    // UTILS
    // ============================================================

    private fun easeInOutCubic(t: Float): Float {
        return if (t < 0.5f)
            4f * t * t * t
        else
            1f - (-2f * t + 2f).pow(3) / 2f
    }

    private fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t
    }
}