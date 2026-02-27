package com.firefinix.freestyle2048game

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)

        findViewById<ImageButton>(R.id.btnHammer).setOnClickListener {
            gameView.onHammerPressed()
        }

        findViewById<ImageButton>(R.id.btnUndo).setOnClickListener {
            gameView.onUndoPressed()
        }

        findViewById<ImageButton>(R.id.btnPause).setOnClickListener {
            gameView.onPausePressed()
        }
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
}