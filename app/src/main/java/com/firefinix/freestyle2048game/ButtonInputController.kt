package com.firefinix.freestyle2048game

class ButtonInputController(
    private val layout: UILayoutController
) {

    private var pressedButton: ButtonType? = null

    fun onTouchDown(x: Float, y: Float): Boolean {

        pressedButton =
            when {

                layout.pauseButtonRect.contains(x, y)
                    -> ButtonType.PAUSE

                layout.soundButtonRect.contains(x, y)
                    -> ButtonType.SOUND

                layout.undoButtonRect.contains(x, y)
                    -> ButtonType.UNDO

                layout.restartButtonRect.contains(x, y)
                    -> ButtonType.RESTART

                layout.settingsButtonRect.contains(x, y)
                    -> ButtonType.SETTINGS

                else -> null
            }

        return pressedButton != null
    }

    fun onTouchUp(): Boolean {

        val wasPressed = pressedButton != null

        pressedButton = null

        return wasPressed
    }

    fun cancel() {

        pressedButton = null
    }

    fun isPressed(type: ButtonType): Boolean {

        return pressedButton == type
    }
}
