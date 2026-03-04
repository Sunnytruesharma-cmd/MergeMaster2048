package com.firefinix.freestyle2048game

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private lateinit var futureText: TextView
    private lateinit var futureTileManager: FutureTileManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)
        futureText = findViewById(R.id.txtFutureTileValue)

        enableImmersiveMode()

        // ================= FUTURE TILE SYSTEM =================

        futureTileManager = FutureTileManager(this)

        if (!futureTileManager.isFreeActive()) {
            futureTileManager.startFreeTimer(20 * 60 * 1000)
        }

        // simple preview tile (temporary)
        updateFuturePreview(Random.nextInt(0,2).let { if(it==0) 2 else 4 })

        // ================= BUTTONS =================

        findViewById<View>(R.id.btnHammer).setOnClickListener {
            gameView.gameManager.useHammer()
        }

        findViewById<View>(R.id.btnSwipe).setOnClickListener {
            gameView.gameManager.useShuffle()
        }

        findViewById<View>(R.id.btnReset).setOnClickListener {
            gameView.gameManager.resetGame()
            updateFuturePreview(Random.nextInt(0,2).let { if(it==0) 2 else 4 })
        }

        findViewById<View>(R.id.btnReward).setOnClickListener {
            // Reward ad logic later
        }

        findViewById<View>(R.id.btnCombo).setOnClickListener {
            // Combo logic later
        }

        // ================= ADS =================

        MobileAds.initialize(this)

        val adContainer = findViewById<FrameLayout>(R.id.adContainer)

        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        adContainer.addView(adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    // ================= PREVIEW UPDATE =================

    private fun updateFuturePreview(value: Int) {

        if (!futureTileManager.isFreeActive()) {
            futureText.text = "?"
            futureText.alpha = 0.5f
            return
        }

        futureText.text = value.toString()
        futureText.alpha = 1f
    }

    // ================= IMMERSIVE MODE =================

    private fun enableImmersiveMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            window.setDecorFitsSystemWindows(false)

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
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
        enableImmersiveMode()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
}