package com.firefinix.freestyle2048game

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    // Top HUD
    private lateinit var txtScore: TextView
    private lateinit var txtGems: TextView
    private lateinit var txtCrowns: TextView
    private lateinit var txtNextTile: TextView

    // Bottom Buttons
    private lateinit var btnHammer: Button
    private lateinit var btnShuffle: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // GameView
        gameView = findViewById(R.id.gameView)

        // Top HUD binding
        txtScore = findViewById(R.id.txtScore)
        txtGems = findViewById(R.id.txtGems)
        txtCrowns = findViewById(R.id.txtCrowns)
        txtNextTile = findViewById(R.id.txtNextTile)

        // Bottom buttons binding
        btnHammer = findViewById(R.id.btnHammer)
        btnShuffle = findViewById(R.id.btnShuffle)
        btnReset = findViewById(R.id.btnReset)

        // Button click listeners
        setupButtonListeners()

        enableImmersiveMode()
    }

    private fun setupButtonListeners() {

        btnHammer.setOnClickListener {
            // TODO: Hammer power logic
        }

        btnShuffle.setOnClickListener {
            // TODO: Shuffle logic
        }

        btnReset.setOnClickListener {
            recreate() // temporary reset
        }
    }

    private fun enableImmersiveMode() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

            window.insetsController?.let { controller ->
                controller.hide(
                    WindowInsets.Type.statusBars() or
                            WindowInsets.Type.navigationBars()
                )
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                (android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enableImmersiveMode()
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