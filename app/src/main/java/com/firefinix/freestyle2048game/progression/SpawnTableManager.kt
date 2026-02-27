package com.firefinix.freestyle2048game.progression

import kotlin.collections.iterator
import kotlin.random.Random

class SpawnTableManager {

    private val spawnTable = mutableMapOf<Int, Float>()

    init {
        resetToDefault()
    }

    fun resetToDefault() {
        spawnTable.clear()
        spawnTable[2] = 0.50f
        spawnTable[4] = 0.15f
        spawnTable[8] = 0.10f
        spawnTable[16] = 0.10f
        spawnTable[32] = 0.05f
        spawnTable[64] = 0.10f
    }

    fun getNextTileValue(): Int {

        val random = Random.Default.nextFloat()
        var cumulative = 0f

        for ((value, probability) in spawnTable) {
            cumulative += probability
            if (random <= cumulative) {
                return value
            }
        }

        return 2
    }

    fun removeTileValue(value: Int) {
        spawnTable.remove(value)
        rebalanceProbabilities()
    }

    private fun rebalanceProbabilities() {

        val total = spawnTable.values.sum()

        if (total == 0f) return

        for (key in spawnTable.keys) {
            spawnTable[key] = spawnTable[key]!! / total
        }
    }

    fun getCurrentTable(): Map<Int, Float> {
        return spawnTable.toMap()
    }
}