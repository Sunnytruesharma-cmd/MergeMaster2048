package com.firefinix.freestyle2048game

class CurrencyManager {

    private var gems: Long = 0L
    private var crowns: Long = 0L

    fun getGems(): Long {
        return gems
    }

    fun getCrowns(): Long {
        return crowns
    }

    fun addGems(amount: Long) {
        gems += amount
    }

    fun addCrowns(amount: Long) {
        crowns += amount
    }

    fun spendGems(amount: Long): Boolean {
        return if (gems >= amount) {
            gems -= amount
            true
        } else {
            false
        }
    }

    fun setGems(value: Long) {
        gems = value
    }

    fun setCrowns(value: Long) {
        crowns = value
    }
}