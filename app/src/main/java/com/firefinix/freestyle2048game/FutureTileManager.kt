package com.firefinix.freestyle2048game

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock

class FutureTileManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("future_tile_prefs", Context.MODE_PRIVATE)

    private val FREE_DURATION_KEY = "free_duration"
    private val TIMER_START_KEY = "timer_start"
    private val FREE_ACTIVE_KEY = "free_active"

    companion object {
        private const val DEFAULT_FREE_DURATION = 20 * 60 * 1000L // 20 minutes
    }

    init {
        if (!prefs.contains(FREE_ACTIVE_KEY)) {
            startFreeTimer(DEFAULT_FREE_DURATION)
        }
    }

    // ================= START TIMER =================

    fun startFreeTimer(durationMillis: Long) {
        prefs.edit()
            .putLong(FREE_DURATION_KEY, durationMillis)
            .putLong(TIMER_START_KEY, SystemClock.elapsedRealtime())
            .putBoolean(FREE_ACTIVE_KEY, true)
            .apply()
    }

    // ================= CHECK STATUS =================

    fun isFreeActive(): Boolean {

        val isActive = prefs.getBoolean(FREE_ACTIVE_KEY, false)
        if (!isActive) return false

        val startTime = prefs.getLong(TIMER_START_KEY, 0L)
        val duration = prefs.getLong(FREE_DURATION_KEY, DEFAULT_FREE_DURATION)

        val elapsed = SystemClock.elapsedRealtime() - startTime

        if (elapsed >= duration) {
            prefs.edit().putBoolean(FREE_ACTIVE_KEY, false).apply()
            return false
        }

        return true
    }

    // ================= REMAINING TIME =================

    fun getRemainingTimeMillis(): Long {

        if (!isFreeActive()) return 0L

        val startTime = prefs.getLong(TIMER_START_KEY, 0L)
        val duration = prefs.getLong(FREE_DURATION_KEY, DEFAULT_FREE_DURATION)

        val elapsed = SystemClock.elapsedRealtime() - startTime
        val remaining = duration - elapsed

        return if (remaining > 0) remaining else 0L
    }

    // ================= FORCE LOCK =================

    fun lockFutureTile() {
        prefs.edit().putBoolean(FREE_ACTIVE_KEY, false).apply()
    }

    // ================= FORCE UNLOCK =================

    fun unlockFutureTile(durationMillis: Long = DEFAULT_FREE_DURATION) {
        startFreeTimer(durationMillis)
    }
}