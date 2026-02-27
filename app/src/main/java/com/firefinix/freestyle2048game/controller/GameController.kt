package com.firefinix.freestyle2048game.controller

import com.firefinix.freestyle2048game.GameManager
import com.firefinix.freestyle2048game.CurrencyManager
import com.firefinix.freestyle2048game.ScoreManager
import com.firefinix.freestyle2048game.persistence.SaveManager
import com.firefinix.freestyle2048game.persistence.GameState
import com.firefinix.freestyle2048game.economy.LifeManager
import com.firefinix.freestyle2048game.economy.RewardManager
import com.firefinix.freestyle2048game.progression.MilestoneManager
import com.firefinix.freestyle2048game.ads.AdsManager
import com.firefinix.freestyle2048game.ads.RewardType

class GameController(
    private val gameManager: GameManager,
    private val milestoneManager: MilestoneManager,
    private val rewardManager: RewardManager,
    private val lifeManager: LifeManager,
    private val currencyManager: CurrencyManager,
    private val scoreManager: ScoreManager,
    private val adsManager: AdsManager,
    private val saveManager: SaveManager
) {

    // ============================================================
    // INTERNAL OBSERVER CACHE
    // ============================================================

    private var lastScore: Long = 0L
    private var lastGems: Long = 0L
    private var lastCrowns: Long = 0L

    // ============================================================
    // ENGINE STATE SYNC (CALL EVERY FRAME)
    // ============================================================

    fun syncFromGame() {

        val currentScore = gameManager.getScore()
        val currentGems = gameManager.getGems()
        val currentCrowns = gameManager.getCrowns()

        // SCORE SYNC
        if (currentScore != lastScore) {
            scoreManager.setScore(currentScore)
            lastScore = currentScore
        }

        // GEMS SYNC
        if (currentGems != lastGems) {
            currencyManager.setGems(currentGems)
            lastGems = currentGems
        }

        // CROWNS SYNC
        if (currentCrowns != lastCrowns) {
            currencyManager.setCrowns(currentCrowns)
            lastCrowns = currentCrowns
        }
    }

    // ============================================================
    // SAVE GAME STATE
    // ============================================================

    fun saveGame() {

        val state = GameState(
            currentScore = scoreManager.getScore(),
            bestScore = scoreManager.getBestScore(),
            gems = currencyManager.getGems(),
            crowns = currencyManager.getCrowns(),
            hearts = lifeManager.getLives(),
            highestTile = 0,
            rewardBonusPercent = 0f,
            removedTiles = emptySet(),
            isDarkTheme = false
        )

        saveManager.saveGameState(state)
    }

    // ============================================================
    // LOAD GAME STATE
    // ============================================================

    fun loadGame() {

        val state = saveManager.loadGameState()

        scoreManager.setScore(state.currentScore)
        scoreManager.setBestScore(state.bestScore)

        currencyManager.setGems(state.gems)
        currencyManager.setCrowns(state.crowns)

        lifeManager.setLives(state.hearts)

        lastScore = state.currentScore
        lastGems = state.gems
        lastCrowns = state.crowns
    }

    // ============================================================
    // GAME OVER HANDLING
    // ============================================================

    fun onGameOver() {
        if (lifeManager.hasLife()) {
            lifeManager.consumeLife()
        }
    }

    // ============================================================
    // REWARDED AD HANDLING
    // ============================================================

    fun onRewardAdRequested(type: RewardType) {

        adsManager.showRewardedAd(type) { success ->

            if (success) {
                rewardManager.applyAdReward(type)
            }
        }
    }
}