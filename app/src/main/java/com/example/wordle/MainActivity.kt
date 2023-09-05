package com.example.wordle

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible


class MainActivity : AppCompatActivity() {

    var wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
    val max_Guesses = 3
    var guesscounter = max_Guesses


    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Variables for the guess button as well as the input for user guesses
        var guessbutton = findViewById<Button>(R.id.guess_button)
        var inputline = findViewById<View>(R.id.input) as EditText

        // Will wrap into object and return object in string form
        var userguess = inputline.text.toString()
        // Reference for the guess output spaces in app
        var guesses = findViewById<TextView>(R.id.user_guesses)
        //Displays the correct answer
        var correctAnswer = findViewById<TextView>(R.id.correct_answer)
        //When game is done, reset the game
        var resetAnswer = findViewById<Button>(R.id.resetbutton)


        guessbutton.setOnClickListener {
//            If the # of guesses are not exceeded
            if(guesscounter > 0) {
                userguess = inputline.text.toString().uppercase()

                if (userguess.length == 4) {
                    guesses.text = "" + guesses.text + userguess + " " + checkGuess(userguess) + " "
                    guesscounter--
//                    clear input line for next user guess
                    inputline.text.clear()
                    hideKeyboard(inputline)
                } else {
                    Toast.makeText(it.context, "Guess must be 4 letters long.", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            //If get correct answer
            if (guesses.text == "0000"){
                guessbutton.isInvisible=true
                Toast.makeText(it.context,"You win this game!",Toast.LENGTH_SHORT).show()
            }

            if(guesscounter <= 0 || wordToGuess.equals(userguess)){
                guessbutton.isInvisible = true
                //Reset button will now reappear
                resetAnswer.isInvisible = false
                Toast.makeText(it.context,"You have exceeded the limit!",Toast.LENGTH_SHORT).show()
                correctAnswer.text = "" + correctAnswer.text + wordToGuess
            }

        }

        resetAnswer.setOnClickListener {
            resetAnswer.setBackgroundColor(Color.BLACK)
            guesses.text = ""
            guesscounter = max_Guesses
            guessbutton.isInvisible = false
            resetAnswer.isInvisible = true
            wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
            correctAnswer.text = "Correct word was: "
        }
    }

    fun hideKeyboard(view: TextView) {
    if (view != null) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

}
