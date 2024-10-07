package com.csi2818.rock_paper_scissors

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csi2818.rock_paper_scissors.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var playerWins = 0
    private var computerWins = 0
    private var gameOver = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rockImageButton.setOnClickListener(::handleHandClick)
        binding.paperImageButton.setOnClickListener(::handleHandClick)
        binding.scissorsImageButton.setOnClickListener(::handleHandClick)

        binding.restartButton.setOnClickListener(::handleRestartClick)
    }

    private fun handleHandClick(view: android.view.View) {

        if (gameOver){
            return
        }

        var playerChoice: HandOptions? = null
        var computerChoice: HandOptions? = null
        var playerRoundResult: RoundResults? = null

        if (view.id == binding.rockImageButton.id) {
            playerChoice = HandOptions.ROCK
        } else if (view.id == binding.paperImageButton.id) {
            playerChoice = HandOptions.PAPER
        } else if (view.id == binding.scissorsImageButton.id){
            playerChoice = HandOptions.SCISSORS
        }

        val randNum = Random.nextInt(1,4)
        if(randNum == 1){
            computerChoice = HandOptions.ROCK
        } else if (randNum == 2){
            computerChoice = HandOptions.PAPER
        } else if (randNum == 3){
            computerChoice = HandOptions.SCISSORS
        }



        if(playerChoice == HandOptions.ROCK && computerChoice == HandOptions.SCISSORS
            || playerChoice == HandOptions.PAPER && computerChoice == HandOptions.ROCK
            || playerChoice == HandOptions.SCISSORS && computerChoice == HandOptions.PAPER){
            playerRoundResult = RoundResults.WIN
        }
        else if (playerChoice == computerChoice){
            playerRoundResult = RoundResults.TIE
        } else {
            playerRoundResult = RoundResults.LOSE
        }



        var newPlayerImg = resources.getIdentifier("@drawable/" + playerChoice?.name?.lowercase(), "drawable", getPackageName())
        binding.playerImageView.setImageResource((newPlayerImg))

        var newComputerImg = resources.getIdentifier("@drawable/" + computerChoice?.name?.lowercase(), "drawable", getPackageName())
        binding.computerImageView.setImageResource((newComputerImg))



        if (playerRoundResult == RoundResults.WIN){
            playerWins++
            binding.playerScoreTextView.text = "Player Score: " + playerWins
            binding.roundWinnerTextView.text = "Player Wins!"
        }else if (playerRoundResult == RoundResults.LOSE){
            computerWins++
            binding.computerScoreTextView.text = "Computer Score: " + computerWins
            binding.roundWinnerTextView.text = "Computer Wins!"
        }else{
            binding.roundWinnerTextView.text = "It's a tie!"
        }

        checkAndHandleWin()

    }

    private fun checkAndHandleWin() {
        if(playerWins >= 10 || computerWins >= 10){
            gameOver = true

            if(playerWins >= 10){
                binding.overallWinnerTextView.text = "The player is the winner!"
            } else {
                binding.overallWinnerTextView.text = "The computer is the winner!"
            }

            binding.restartButton.visibility = View.VISIBLE
        }



    }

    private fun handleRestartClick(view: android.view.View){

        if( !gameOver ){
            return
        }

        binding.restartButton.visibility = View.INVISIBLE

        playerWins = 0
        computerWins = 0
        gameOver = false

        binding.overallWinnerTextView.text = "No winner yet"
        binding.roundWinnerTextView.text = ""
        binding.playerScoreTextView.text = "Player Score: " + playerWins
        binding.computerScoreTextView.text = "Computer Score: " + computerWins


    }



}

enum class HandOptions {
    ROCK, PAPER, SCISSORS
}

enum class RoundResults{
    WIN, TIE, LOSE
}

