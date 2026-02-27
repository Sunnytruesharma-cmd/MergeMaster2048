package com.firefinix.freestyle2048game.ads

import com.firefinix.freestyle2048game.ads.RewardType

class AdsManager {

    fun showRewardedAd(
        type: RewardType,
        onResult: (Boolean) -> Unit
    ) {

        // TODO: Integrate Google AdMob rewarded ad here

        // Temporary simulation
        simulateAdSuccess(onResult)
    }

    private fun simulateAdSuccess(
        onResult: (Boolean) -> Unit
    ) {
        onResult(true)
    }

    fun showInterstitial() {
        // TODO: Interstitial ad implementation
    }
}