package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.triviagame.model.Trivia
import com.example.triviagame.ui.TriviaViewModel
import com.example.triviagame.ui.TriviaViewModelFactory


class MainActivity : AppCompatActivity() {

    private val triviaViewModel: TriviaViewModel by viewModels {
        TriviaViewModelFactory((application as TriviaApplication).repository)
    }


    private fun scrambleQuestions(trivia: Trivia) : MutableList<String> {
        var potentialAnswers: MutableList<String> = mutableListOf()

        potentialAnswers.add(trivia.correctAnswer)
        trivia.incorrectAnswers.map{ potentialAnswers.add(it)}

        potentialAnswers.shuffle()
        return potentialAnswers
    }

   //TODO add functionlity to check answer and make buttons work
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

       val question = findViewById<TextView>(R.id.question_text)
       val answerOne = findViewById<Button>(R.id.answer_1)
       val answerTwo = findViewById<Button>(R.id.answer_2)
       val answerThree = findViewById<Button>(R.id.answer_3)
       val answerFour = findViewById<Button>(R.id.answer_4)
       val resultMessage = findViewById<TextView>(R.id.result_message)


        // Create the observer which updates the UI.
        val triviaObserver = Observer<Trivia> { newTrivia ->
            question.text = newTrivia.question

            val potentialAnswers = scrambleQuestions(newTrivia)

            answerOne.text = potentialAnswers[0]
            answerTwo.text = potentialAnswers[1]
            answerThree.text = potentialAnswers[2]
            answerFour.text = potentialAnswers[3]
        }



    triviaViewModel.nextUnansweredTrivia.observe(this, triviaObserver)
    }





    fun getCorrectAnswer(trivia: Trivia): String {
        return trivia.correctAnswer
    }
}