package com.firefinix.freestyle2048game.economy

class LifeManager {

    private var lives: Int = 3

    fun hasLife(): Boolean {
        return lives > 0
    }

    fun consumeLife() {
        if (lives > 0) {
            lives--
        }
    }

    fun addLife() {
        lives++
    }

    fun getLives(): Int {
        return lives
    }

    fun setLives(value: Int) {
        lives = value
    }
}