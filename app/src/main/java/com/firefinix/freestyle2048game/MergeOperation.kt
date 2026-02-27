package com.firefinix.freestyle2048game

data class MergeOperation(

    val anchor: Tile,

    val sources: List<Tile>,

    val finalValue: Int,

    var animationStarted: Boolean = false
)
