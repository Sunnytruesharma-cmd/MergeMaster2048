package com.firefinix.freestyle2048game.persistence

data class GameState(

    val currentScore: Long = 0L,
    val bestScore: Long = 0L,

    val gems: Long = 0L,
    val crowns: Long = 0L,

    val hearts: Int = 3,

    val highestTile: Int = 0,
    val rewardBonusPercent: Float = 0f,

    val removedTiles: Set<Int> = emptySet(),

    val isDarkTheme: Boolean = false
)