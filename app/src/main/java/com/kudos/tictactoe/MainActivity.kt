package com.kudos.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.kudos.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TicTacToeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up the game status text view
        val gameStatusTextView = binding.textGameStatus

        // Set up the grid buttons
        val gridButtons = arrayOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )

        // Observe the game state changes
        viewModel.gameState.observe(this) { gameState ->
            updateUI(gameState, gameStatusTextView, gridButtons)
        }

        // Set click listeners for each grid button
        for (i in gridButtons.indices) {
            val row = i / 3
            val col = i % 3
            gridButtons[i].setOnClickListener {
                viewModel.makeMove(row, col)
            }
        }
        // Set click listener for restart game button
        binding.btnRestart.setOnClickListener {
            viewModel.restartGame()
        }
    }

    private fun updateUI(gameState: GameState, gameStatusTextView: TextView, gridButtons: Array<Button>) {
        when (gameState) {
            GameState.IN_PROGRESS -> {
                gameStatusTextView.text = "Player ${viewModel.currentPlayer.value}'s Turn"
                gameStatusTextView.visibility = View.VISIBLE
            }
            GameState.PLAYER_X_WON -> {
                gameStatusTextView.text = "Player X Wins!"
                gameStatusTextView.visibility = View.VISIBLE
                disableAllButtons(gridButtons)
            }
            GameState.PLAYER_O_WON -> {
                gameStatusTextView.text = "Player O Wins!"
                gameStatusTextView.visibility = View.VISIBLE
                disableAllButtons(gridButtons)
            }
            GameState.DRAW -> {
                gameStatusTextView.text = "It's a Draw!"
                gameStatusTextView.visibility = View.VISIBLE
                disableAllButtons(gridButtons)
            }
        }
        // Update grid buttons with "X" or "O"
        for (i in gridButtons.indices) {
            val row = i / 3
            val col = i % 3
            val player = viewModel.model.board[row][col]
            gridButtons[i].text = player?.name ?: ""  // Display "X" or "O" on the button
            gridButtons[i].isEnabled = player == null  // Disable button if already clicked
        }
    }

    private fun disableAllButtons(gridButtons: Array<Button>) {
        for (button in gridButtons) {
            button.isEnabled = false
        }
    }
}