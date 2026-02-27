package com.firefinix.freestyle2048game.effects

class SoundManager {

    // ============================================================
    // BUTTON SOUNDS
    // ============================================================

    fun playButtonClick() {
        // TODO: load and play click sound
    }

    fun playPauseSound() {
        // TODO
    }

    // ============================================================
    // MERGE SOUNDS
    // ============================================================

    fun playMergeSound(mergeCount: Int) {

        when (mergeCount) {
            2 -> playSmallMerge()
            3 -> playMediumMerge()
            4 -> playLargeMerge()
            else -> playEpicMerge()
        }

        playVoiceLine(mergeCount)
    }

    // ============================================================
    // VOICE LINES
    // ============================================================

    private fun playVoiceLine(mergeCount: Int) {

        when {
            mergeCount == 2 -> playVoice("Nice")
            mergeCount == 3 -> playVoice("Good")
            mergeCount == 4 -> playVoice("Great")
            mergeCount == 5 -> playVoice("Awesome")
            mergeCount >= 6 -> playVoice("Excellent")
        }
    }

    private fun playVoice(text: String) {
        // Hook TTS or audio file
    }

    // ============================================================
    // INTERNAL SOUND TYPES
    // ============================================================

    private fun playSmallMerge() {}
    private fun playMediumMerge() {}
    private fun playLargeMerge() {}
    private fun playEpicMerge() {}

    fun playMilestoneSound() {
        // Special celebration sound
    }

    fun playPopupSound() {
        // Popup open sound
    }
}