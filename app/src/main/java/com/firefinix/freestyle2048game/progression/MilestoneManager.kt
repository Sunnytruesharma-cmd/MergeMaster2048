package com.firefinix.freestyle2048game.progression

import com.firefinix.freestyle2048game.persistence.SaveManager
import com.firefinix.freestyle2048game.persistence.GameState

class MilestoneManager(
    private val spawnTableManager: SpawnTableManager,
    private val saveManager: SaveManager
) {

    private var highestTile: Int = 0
    private var rewardBonusPercent: Float = 0f

    private val removedTiles = mutableSetOf<Int>()

    fun checkMilestone(tileValue: Int) {

        if (tileValue > highestTile) {
            highestTile = tileValue
        }

        when (tileValue) {

            2048 -> {
                spawnTableManager.removeTileValue(2)
                removedTiles.add(2)
            }

            4096 -> {
                spawnTableManager.removeTileValue(4)
                removedTiles.add(4)
            }

            else -> {
                if (tileValue >= 8192) {
                    rewardBonusPercent += 0.03f
                }
            }
        }

        persistState()
    }

    private fun persistState() {

        val state = saveManager.loadGameState()

        val updated = state.copy(
            highestTile = highestTile,
            rewardBonusPercent = rewardBonusPercent,
            removedTiles = removedTiles
        )

        saveManager.saveGameState(updated)
    }
}