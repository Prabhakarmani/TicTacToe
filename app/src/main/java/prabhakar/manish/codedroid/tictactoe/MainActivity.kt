package prabhakar.manish.codedroid.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import prabhakar.manish.codedroid.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TicTacToeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)

        // Observe the game state and update the buttons
        viewModel.gameState.observe(this) { cells ->
            updateButtons(cells)
        }

        // Observe the game message and show it
        viewModel.message.observe(this) { message ->
            showMessage(message)
        }
    }

    private fun updateButtons(cells: List<Int>) {
        val buttons = listOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        for ((index, cell) in cells.withIndex()) {
            val button = buttons[index] as MaterialButton
            button.text = getButtonText(cell)
            button.isEnabled = cell == 0
        }
    }

    private fun getButtonText(cell: Int): String {
        return when (cell) {
            1 -> "X"
            2 -> "O"
            else -> ""
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun buttonClick(view: View) {
        val button = view as Button
        when (button.id) {
            R.id.button1 -> viewModel.buttonClick(0)
            R.id.button2 -> viewModel.buttonClick(1)
            R.id.button3 -> viewModel.buttonClick(2)
            R.id.button4 -> viewModel.buttonClick(3)
            R.id.button5 -> viewModel.buttonClick(4)
            R.id.button6 -> viewModel.buttonClick(5)
            R.id.button7 -> viewModel.buttonClick(6)
            R.id.button8 -> viewModel.buttonClick(7)
            R.id.button9 -> viewModel.buttonClick(8)
        }
    }

    fun restartGame(view: View) {
        viewModel.restartGame()
    }

    fun PlayerChoose(view: View) {
        val ps = view as Button
        when (ps.id) {
            R.id.PVP -> {
                viewModel.setPlayerMode(TicTacToeViewModel.PlayerMode.PVP)
                ps.setBackgroundColor(getColor(R.color.colorPrimary))
                binding.PVC.setBackgroundColor(getColor(android.R.color.transparent))
            }

            R.id.PVC -> {
                viewModel.setPlayerMode(TicTacToeViewModel.PlayerMode.PVC)
                ps.setBackgroundColor(getColor(R.color.colorPrimary))
                binding.PVP.setBackgroundColor(getColor(android.R.color.transparent))
            }
        }
    }
}
