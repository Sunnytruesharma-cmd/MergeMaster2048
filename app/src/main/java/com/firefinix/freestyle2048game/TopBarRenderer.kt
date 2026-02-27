package com.firefinix.freestyle2048game

import android.content.Context
import android.graphics.*

class TopBarRenderer(
    private val context: Context,
    private val screenWidth: Float,
    private val screenHeight: Float
) {

    private val barHeight = screenHeight * 0.14f
    private val padding = barHeight * 0.18f

    private val capsuleHeight = barHeight * 0.6f
    private val capsuleRadius = capsuleHeight / 2f

    private val iconSize = capsuleHeight * 0.6f

    // Capsules
    private val heartRect: RectF
    private val crownRect: RectF

    private val heartIconX: Float
    private val heartIconY: Float

    private val crownIconX: Float
    private val crownIconY: Float

    private val heartBitmap: Bitmap
    private val crownBitmap: Bitmap

    init {

        heartBitmap = loadScaledBitmap(R.drawable.ic_heart)
        crownBitmap = loadScaledBitmap(R.drawable.ic_crown_premium)

        // LEFT CAPSULE
        val left = padding
        val top = padding
        val right = left + screenWidth * 0.28f
        val bottom = top + capsuleHeight

        heartRect = RectF(left, top, right, bottom)

        heartIconX = left + capsuleHeight * 0.6f
        heartIconY = heartRect.centerY()

        // RIGHT CAPSULE
        val rightEdge = screenWidth - padding
        val crownLeft = rightEdge - screenWidth * 0.28f

        crownRect = RectF(crownLeft, top, rightEdge, bottom)

        crownIconX = crownLeft + capsuleHeight * 0.6f
        crownIconY = crownRect.centerY()
    }

    // ============================================================

    fun onTouchDown(x: Float, y: Float) {}
    fun onTouchUp() {}

    fun render(
        canvas: Canvas,
        hearts: Int,
        crowns: Long,
        score: Long,
        bestScore: Long
    ) {

        // Draw capsules
        canvas.drawRoundRect(heartRect, capsuleRadius, capsuleRadius, capsulePaint)
        canvas.drawRoundRect(crownRect, capsuleRadius, capsuleRadius, capsulePaint)

        // Icons
        drawIcon(canvas, heartBitmap, heartIconX, heartIconY)
        drawIcon(canvas, crownBitmap, crownIconX, crownIconY)

        // Capsule text
        drawCapsuleText(canvas, hearts.toString(), heartRect, heartIconX)
        drawCapsuleText(canvas, formatValue(crowns), crownRect, crownIconX)

        // Center Score
        drawCenterScore(canvas, score, bestScore)
    }

    // ============================================================

    private fun drawCenterScore(canvas: Canvas, score: Long, best: Long) {

        val centerX = screenWidth / 2f

        val scoreText = formatValue(score)
        val bestText = "Best: ${formatValue(best)}"

        canvas.drawText(
            scoreText,
            centerX,
            barHeight * 0.6f,
            scorePaint
        )

        canvas.drawText(
            bestText,
            centerX,
            barHeight * 0.9f,
            bestPaint
        )
    }

    private fun drawCapsuleText(
        canvas: Canvas,
        text: String,
        rect: RectF,
        iconCenterX: Float
    ) {
        val x = iconCenterX + iconSize * 0.9f
        val y = rect.centerY() - (capsuleTextPaint.descent() + capsuleTextPaint.ascent()) / 2
        canvas.drawText(text, x, y, capsuleTextPaint)
    }

    private fun drawIcon(canvas: Canvas, bitmap: Bitmap, cx: Float, cy: Float) {
        val half = iconSize / 2f
        val rect = RectF(cx - half, cy - half, cx + half, cy + half)
        canvas.drawBitmap(bitmap, null, rect, null)
    }

    private fun formatValue(value: Long): String {
        return when {
            value >= 1_000_000_000 -> "${value / 1_000_000_000}B"
            value >= 1_000_000 -> "${value / 1_000_000}M"
            value >= 1_000 -> "${value / 1_000}K"
            else -> value.toString()
        }
    }

    private fun loadScaledBitmap(resId: Int): Bitmap {
        val original = BitmapFactory.decodeResource(context.resources, resId)
        return Bitmap.createScaledBitmap(
            original,
            iconSize.toInt(),
            iconSize.toInt(),
            true
        )
    }

    // ============================================================

    private val capsulePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1E1E1E")
    }

    private val capsuleTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = capsuleHeight * 0.38f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val scorePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = barHeight * 0.45f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val bestPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        textAlign = Paint.Align.CENTER
        textSize = barHeight * 0.28f
    }
}