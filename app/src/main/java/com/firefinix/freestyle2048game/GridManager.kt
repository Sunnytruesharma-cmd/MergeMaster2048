package com.firefinix.freestyle2048game

class GridManager(
    private val cols: Int = 5,
    private val rows: Int = 8
) {

    private val grid = Array(rows) { IntArray(cols) }

    fun getCols(): Int = cols
    fun getRows(): Int = rows

    fun getValue(row: Int, col: Int): Int {
        return grid[row][col]
    }

    fun isColumnFull(col: Int): Boolean {
        return grid[0][col] != 0
    }

    fun placeTile(col: Int, value: Int): Boolean {

        if (isColumnFull(col)) return false

        // find lowest empty row
        for (row in rows - 1 downTo 0) {
            if (grid[row][col] == 0) {
                grid[row][col] = value
                handleMerge(row, col)
                return true
            }
        }

        return false
    }

    private fun handleMerge(startRow: Int, col: Int) {

        var row = startRow

        while (row > 0) {

            if (grid[row][col] == grid[row - 1][col] &&
                grid[row][col] != 0
            ) {

                grid[row - 1][col] *= 2
                grid[row][col] = 0

                shiftColumnDown(col)
                row--

            } else {
                break
            }
        }
    }

    private fun shiftColumnDown(col: Int) {

        for (row in rows - 1 downTo 1) {
            if (grid[row][col] == 0) {
                grid[row][col] = grid[row - 1][col]
                grid[row - 1][col] = 0
            }
        }
    }

    fun isGameOver(): Boolean {
        for (c in 0 until cols) {
            if (!isColumnFull(c)) return false
        }
        return true
    }
}
