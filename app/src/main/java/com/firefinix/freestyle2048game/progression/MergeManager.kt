package com.firefinix.freestyle2048game.progression

import com.firefinix.freestyle2048game.Tile

class MergeManager(
    private val rows: Int,
    private val cols: Int
) {

    data class MergeResult(
        val anchorRow: Int,
        val anchorCol: Int,
        val mergedTiles: List<Pair<Int, Int>>,
        val finalValue: Int
    )

    fun detectMerge(
        grid: Array<Array<Tile?>>,
        anchorRow: Int,
        anchorCol: Int
    ): MergeResult? {

        val anchor = grid[anchorRow][anchorCol] ?: return null
        val targetValue = anchor.value

        val visited = Array(rows) { BooleanArray(cols) }
        val cluster = ArrayList<Pair<Int, Int>>()

        floodFill(
            grid,
            anchorRow,
            anchorCol,
            targetValue,
            visited,
            cluster
        )

        if (cluster.size < 2)
            return null

        val finalValue =
            targetValue shl (cluster.size - 1)

        return MergeResult(
            anchorRow,
            anchorCol,
            cluster,
            finalValue
        )
    }

    private fun floodFill(
        grid: Array<Array<Tile?>>,
        row: Int,
        col: Int,
        value: Int,
        visited: Array<BooleanArray>,
        cluster: MutableList<Pair<Int, Int>>
    ) {

        if (row !in 0 until rows || col !in 0 until cols)
            return

        if (visited[row][col])
            return

        val tile = grid[row][col] ?: return
        if (tile.value != value)
            return

        visited[row][col] = true
        cluster.add(Pair(row, col))

        floodFill(grid, row + 1, col, value, visited, cluster)
        floodFill(grid, row - 1, col, value, visited, cluster)
        floodFill(grid, row, col + 1, value, visited, cluster)
        floodFill(grid, row, col - 1, value, visited, cluster)
    }
}