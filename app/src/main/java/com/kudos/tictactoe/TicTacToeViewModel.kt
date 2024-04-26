package com.kudos.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicTacToeViewModel: ViewModel() {

    val model = TicTacToeModel()
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<Player> = _currentPlayer

    init {
        resetGame()
        _gameState.value = model.gameState
        _currentPlayer.value = Player.X  // Start with Player X
    }

    fun makeMove(row: Int, col: Int) {
        val cell = Cell(row, col)
        if (model.makeMove(cell)) {
            _gameState.value = model.gameState
            _currentPlayer.value = if (_currentPlayer.value == Player.X) Player.O else Player.X
        }
    }

    fun restartGame() {
        resetGame()
        _gameState.value = GameState.IN_PROGRESS
        _currentPlayer.value = Player.X  // Start with Player X
    }

    private fun resetGame() {
        model.resetGame()
        _currentPlayer.value = Player.X  // Start with Player X
    }

}