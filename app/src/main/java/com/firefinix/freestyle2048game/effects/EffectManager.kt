package com.firefinix.freestyle2048game.effects

class EffectManager {

    // ============================================================
    // MERGE EFFECT
    // ============================================================

    fun triggerMergeEffect(mergeCount: Int) {

        when {
            mergeCount >= 6 -> showConfetti()
            mergeCount >= 4 -> showBigPopup()
            else -> showSmallPopup()
        }
    }

    // ============================================================
    // TILE BOUNCE
    // ============================================================

    fun triggerTileBounce() {
        // Hook for tile pop animation
    }

    // ============================================================
    // GHOST HIGHLIGHT
    // ============================================================

    fun triggerGhostHighlight() {
        // Hook for spawner ghost glow
    }

    // ============================================================
    // MILESTONE EFFECT
    // ============================================================

    fun triggerMilestoneCelebration() {
        showConfetti()
    }

    // ============================================================
    // INTERNAL VISUAL EFFECTS
    // ============================================================

    private fun showConfetti() {
        // Hook ParticleSystem
    }

    private fun showBigPopup() {
        // Hook ComboPopup / ScorePopup
    }

    private fun showSmallPopup() {
        // Small feedback animation
    }
}