package com.example.triviagame.ui

sealed class TriviaEffect {

    //data classes are instances that have properties, diff text
    data class UpdateQuestion(val text: String) : TriviaEffect()
    data class UpdateAnswers(val answers: MutableList<String>) : TriviaEffect()
    data class AnsweredCorrectly(val message: String) :
        TriviaEffect()

    data class AnsweredIncorrectly(val message: String) :
        TriviaEffect()

    //only one loading state
    object Loading : TriviaEffect()
}