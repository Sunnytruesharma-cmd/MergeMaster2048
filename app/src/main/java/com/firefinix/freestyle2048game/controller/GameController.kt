package com.firefinix.freestyle2048game

class GameController(
    private val gameView: GameView
) {

    private val gameManager: GameManager?
        get() = gameView.gameManager

    fun onHammerPressed() {
        gameManager?.useHammer()
    }

    fun onShufflePressed() {
        gameManager?.useShuffle()
    }

    fun onResetPressed() {
        gameManager?.resetGame()
    }
}