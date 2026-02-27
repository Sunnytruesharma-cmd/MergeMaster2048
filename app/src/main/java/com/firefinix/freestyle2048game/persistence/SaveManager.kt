package com.firefinix.freestyle2048game.persistence

import android.content.Context
import org.json.JSONArray

class SaveManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("finix2048_save", Context.MODE_PRIVATE)

    // ============================================================
    // SAVE FULL STATE
    // ============================================================

    fun saveGameState(state: GameState) {

        val editor = prefs.edit()

        editor.putLong("currentScore", state.currentScore)
        editor.putLong("bestScore", state.bestScore)

        editor.putLong("gems", state.gems)
        editor.putLong("crowns", state.crowns)

        editor.putInt("hearts", state.hearts)

        editor.putInt("highestTile", state.highestTile)
        editor.putFloat("rewardBonusPercent", state.rewardBonusPercent)

        editor.putBoolean("isDarkTheme", state.isDarkTheme)

        val jsonArray = JSONArray()
        state.removedTiles.forEach { jsonArray.put(it) }

        editor.putString("removedTiles", jsonArray.toString())

        editor.apply()
    }

    // ============================================================
    // LOAD FULL STATE
    // ============================================================

    fun loadGameState(): GameState {

        val removedSet = mutableSetOf<Int>()

        val removedJson =
            prefs.getString("removedTiles", null)

        if (removedJson != null) {

            val jsonArray = JSONArray(removedJson)

            for (i in 0 until jsonArray.length()) {
                removedSet.add(jsonArray.getInt(i))
            }
        }

        return GameState(
            currentScore = prefs.getLong("currentScore", 0L),
            bestScore = prefs.getLong("bestScore", 0L),

            gems = prefs.getLong("gems", 0L),
            crowns = prefs.getLong("crowns", 0L),

            hearts = prefs.getInt("hearts", 3),

            highestTile = prefs.getInt("highestTile", 0),
            rewardBonusPercent = prefs.getFloat("rewardBonusPercent", 0f),

            removedTiles = removedSet,

            isDarkTheme = prefs.getBoolean("isDarkTheme", false)
        )
    }

    // ============================================================
    // RESET
    // ============================================================

    fun resetAll() {
        prefs.edit().clear().apply()
    }
}