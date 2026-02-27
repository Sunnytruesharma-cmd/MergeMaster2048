package com.firefinix.freestyle2048game

class ScoreManager {

    private var score: Long = 0L
    private var bestScore: Long = 0L

    fun addScore(value: Long) {
        score += value
        if (score > bestScore) {
            bestScore = score
        }
    }

    fun getScore(): Long {
        return score
    }

    fun getBestScore(): Long {
        return bestScore
    }

    fun setScore(value: Long) {
        score = value
    }

    fun setBestScore(value: Long) {
        bestScore = value
    }

    fun reset() {
        score = 0L
    }
}