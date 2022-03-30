package com.example.triviagame.ui


data class TriviaViewState(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String,
    val answerState: AnswerState
)

enum class AnswerState {
    AnsweredCorrectly,
    AnsweredIncorrectly,
    Unanswered,
    Skipped
}


