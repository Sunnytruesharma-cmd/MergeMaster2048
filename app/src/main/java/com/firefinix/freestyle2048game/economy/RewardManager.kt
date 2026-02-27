package com.firefinix.freestyle2048game.economy

import com.firefinix.freestyle2048game.CurrencyManager
import com.firefinix.freestyle2048game.ScoreManager
import com.firefinix.freestyle2048game.persistence.SaveManager
import com.firefinix.freestyle2048game.ads.RewardType

class RewardManager(
    private val currencyManager: CurrencyManager,
    private val scoreManager: ScoreManager,
    private val lifeManager: LifeManager,
    private val saveManager: SaveManager
) {

    private var rewardMultiplier: Float = 1f

    fun handleMergeReward(tileValue: Int, mergeCount: Int) {

        val baseScore = tileValue.toLong()
        val finalScore = (baseScore * rewardMultiplier).toLong()

        scoreManager.addScore(finalScore)

        currencyManager.addGems(tileValue / 4L)
        currencyManager.addCrowns(tileValue / 8L)
    }

    fun applyAdReward(type: RewardType) {

        when (type) {

            RewardType.EXTRA_LIFE -> lifeManager.addLife()

            RewardType.DOUBLE_REWARD -> rewardMultiplier = 2f

            RewardType.FREE_HAMMER -> currencyManager.addGems(50)

            RewardType.GIFT_BOX -> currencyManager.addGems(25)
        }
    }
}