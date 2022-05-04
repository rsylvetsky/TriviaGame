package com.example.triviagame.ui

import com.airbnb.mvrx.MavericksState
import com.example.triviagame.model.TriviaStatus


data class TriviaViewState(
    val id: Long = 0,
    val question: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswer: String = "",
    val answerState: TriviaStatus = TriviaStatus.UNANSWERED
) : MavericksState
