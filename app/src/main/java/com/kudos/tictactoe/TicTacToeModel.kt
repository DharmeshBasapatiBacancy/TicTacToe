package com.kudos.tictactoe

data class Cell(val row: Int, val col: Int)

enum class Player { X, O }

enum class GameState { IN_PROGRESS, PLAYER_X_WON, PLAYER_O_WON, DRAW }

class TicTacToeModel {
    val board = Array(3) { Array<Player?>(3) { null } }
    private var currentPlayer = Player.X
    var gameState = GameState.IN_PROGRESS
        private set

    fun makeMove(cell: Cell): Boolean {
        if (gameState != GameState.IN_PROGRESS || board[cell.row][cell.col] != null) {
            return false
        }
        board[cell.row][cell.col] = currentPlayer
        if (checkWin(cell)) {
            gameState = if (currentPlayer == Player.X) GameState.PLAYER_X_WON else GameState.PLAYER_O_WON
        } else if (isBoardFull()) {
            gameState = GameState.DRAW
        } else {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
        return true
    }

    private fun checkWin(cell: Cell): Boolean {
        val row = cell.row
        val col = cell.col
        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
            return true
        }
        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
            return true
        }
        // Check diagonal
        return (row == col || row + col == 2) &&
                ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                        (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer))
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) {
                    return false
                }
            }
        }
        return true
    }


    fun resetGame() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null
            }
        }
        currentPlayer = Player.X
        gameState = GameState.IN_PROGRESS
    }
}