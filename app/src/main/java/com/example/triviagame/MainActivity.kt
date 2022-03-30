package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.triviagame.model.Trivia
import com.example.triviagame.ui.*


class MainActivity : AppCompatActivity() {

    private val triviaViewModel: TriviaViewModel by viewModels {
        TriviaViewModelFactory((application as TriviaApplication).repository)
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
       val nextQuestion = findViewById<Button>(R.id.next_question_button)



        answerOne.setOnClickListener { triviaViewModel.submitAnswer(0) }
        answerTwo.setOnClickListener {triviaViewModel.submitAnswer(1)  }
        answerThree.setOnClickListener { triviaViewModel.submitAnswer(2) }
        answerFour.setOnClickListener { triviaViewModel.submitAnswer(3) }




        // Create the observer which updates the UI.
        val triviaObserver = Observer<TriviaViewState> { newTrivia ->
            question.text = newTrivia.question
            answerOne.text = newTrivia.answers[0]
            answerTwo.text = newTrivia.answers[1]
            answerThree.text = newTrivia.answers[2]
            answerFour.text = newTrivia.answers[3]

            when (newTrivia.answerState) {
                AnswerState.AnsweredCorrectly -> {
                    resultMessage.visibility = View.VISIBLE
                    resultMessage.text = "Correct"
                }
                AnswerState.AnsweredIncorrectly -> {
                    resultMessage.visibility = View.VISIBLE
                    resultMessage.text = "Try again"
                }
                else -> {
                    resultMessage.visibility = View.GONE
                }
            }
        }

        triviaViewModel.viewState.observe(this, triviaObserver)



//            question.text = newTrivia.question
//
//            val potentialAnswers = scrambleQuestions(newTrivia)
//
//            answerOne.text = potentialAnswers[0]
//            answerTwo.text = potentialAnswers[1]
//            answerThree.text = potentialAnswers[2]
//            answerFour.text = potentialAnswers[3]
//



//        answerOne.setOnClickListener {
//
//                //different ways, enum, pass 0
//                triviaViewModel.submitAnswer(0)
//
//
//                if(answerOne.text == getCorrectAnswer(newTrivia)) {
//                    resultMessage.text = getText(R.string.correct_message)
//                    resultMessage.visibility = View.VISIBLE
//                }else {
//                    resultMessage.text = getText(R.string.incorrect_message)
//                    resultMessage.visibility = View.VISIBLE
//                }
//            }
//
//            answerTwo.setOnClickListener {
//                if(answerTwo.text == getCorrectAnswer(newTrivia)) {
//                    resultMessage.text = getText(R.string.correct_message)
//                    resultMessage.visibility = View.VISIBLE
//                }else {
//                    resultMessage.text = getText(R.string.incorrect_message)
//                    resultMessage.visibility = View.VISIBLE
//                }
//            }
//
//            answerThree.setOnClickListener {
//                if(answerThree.text == getCorrectAnswer(newTrivia)) {
//                    resultMessage.text = getText(R.string.correct_message)
//                    resultMessage.visibility = View.VISIBLE
//                }else {
//                    resultMessage.text = getText(R.string.incorrect_message)
//                    resultMessage.visibility = View.VISIBLE
//                }
//            }
//
//            answerFour.setOnClickListener {
//                if(answerFour.text == getCorrectAnswer(newTrivia)) {
//                    resultMessage.text = getText(R.string.correct_message)
//                    resultMessage.visibility = View.VISIBLE
//                }else {
//                    resultMessage.text = getText(R.string.incorrect_message)
//                    resultMessage.visibility = View.VISIBLE
//                }
//            }
//        }
    }


}