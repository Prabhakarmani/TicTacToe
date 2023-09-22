package prabhakar.manish.codedroid.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    private val _gameState = MutableLiveData<List<Int>>()
    val gameState: LiveData<List<Int>> = _gameState

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private var currentPlayer = 1
    private var cells = MutableList(9) { 0 }

    enum class PlayerMode {
        PVP,
        PVC
    }

    private var playerMode = PlayerMode.PVP

    init {
        _gameState.value = cells
        _message.value = ""
    }

    fun buttonClick(cellId: Int) {
        if (cells[cellId] == 0) {
            cells[cellId] = currentPlayer
            _gameState.value = cells
            if (checkWinner(currentPlayer)) {
                _message.value = "Player $currentPlayer Wins!"
            } else if (cells.none { it == 0 }) {
                _message.value = "It's a Draw!"
            } else {
                currentPlayer = 3 - currentPlayer
                if (playerMode == PlayerMode.PVC && currentPlayer == 2) {
                    // Simulate CPU move in PVC mode
                    simulateCPUMove()
                }
            }
        }
    }

    private fun simulateCPUMove() {
        // Implement your CPU move logic here
        // For example, you can randomly choose an empty cell
        val emptyCells = cells.indices.filter { cells[it] == 0 }
        if (emptyCells.isNotEmpty()) {
            val randomIndex = emptyCells.random()
            buttonClick(randomIndex)
        }
    }

    fun restartGame() {
        cells = MutableList(9) { 0 }
        _gameState.value = cells
        _message.value = ""
        currentPlayer = 1
    }

    private fun checkWinner(player: Int): Boolean {
        val winningCombinations = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            if (cells[combination[0]] == player && cells[combination[1]] == player && cells[combination[2]] == player) {
                return true
            }
        }
        return false
    }

    fun setPlayerMode(mode: PlayerMode) {
        playerMode = mode
    }
}
