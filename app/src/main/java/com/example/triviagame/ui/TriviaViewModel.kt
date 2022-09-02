package com.example.triviagame.ui

import androidx.lifecycle.*
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.example.triviagame.TriviaApplication
import com.example.triviagame.model.Trivia
import com.example.triviagame.model.TriviaStatus
import com.example.triviagame.repo.TriviaRepository
import kotlinx.coroutines.launch

class TriviaViewModel(initialState: TriviaViewState, private val repository: TriviaRepository) :
    MavericksViewModel<TriviaViewState>(initialState) {

    companion object : MavericksViewModelFactory<TriviaViewModel, TriviaViewState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: TriviaViewState
        ): TriviaViewModel {
            val repository: TriviaRepository = viewModelContext.app<TriviaApplication>().repository
            return TriviaViewModel(state, repository)
        }
    }

    private var trivia: LiveData<Trivia> = repository.nextUnansweredTrivia

    init {
        repository.loadItems() //move this into background job every so often, put into application initializer,
        initializeViewState()

//        suspend {
//            repository.nextUnansweredTrivia
//        }.execute { copy( //update things to async<trivia> type ) }
//    }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(trivia: Trivia) = viewModelScope.launch {
        repository.insert(trivia)
    }

    fun update(trivia: Trivia) = viewModelScope.launch {
        repository.update(trivia)
    }

    fun initializeViewState() {
//transform livedata trivia to livedata of VS

        Transformations.map(trivia) { triv ->
            val allAnswers = scrambleQuestions(triv)
            TriviaViewState(
                triv.id,
                triv.question,
                allAnswers,
                triv.correctAnswer,
                TriviaStatus.UNANSWERED
            )
        }.observeForever {
            setState {
                copy(
                    id = it.id,
                    question = it.question,
                    answers = it.answers,
                    correctAnswer = it.correctAnswer,
                    answerState = it.answerState
                )
            }
        }

    }

    fun submitAnswer(index: Int) {

        withState { state ->
            if (index == 4) {
                setState {
                    copy(
                        answerState = TriviaStatus.SKIPPED
                    )
                }
                repository.updateStatus(state.id, TriviaStatus.SKIPPED)
            }
            else if (state.answers[index] == state.correctAnswer) {
                setState {
                    copy(
                        answerState = TriviaStatus.ANSWERED_CORRECTLY
                    )
                }
                repository.updateStatus(state.id, TriviaStatus.ANSWERED_CORRECTLY)

            } else {
                setState {
                    copy(
                        answerState = TriviaStatus.ANSWERED_INCORRECTLY
                    )
                }
                repository.updateStatus(state.id, TriviaStatus.ANSWERED_INCORRECTLY)
            }
        }
    }
}

private fun scrambleQuestions(trivia: Trivia): MutableList<String> {
    var potentialAnswers: MutableList<String> = mutableListOf()

    potentialAnswers.add(trivia.correctAnswer)
    trivia.incorrectAnswers.map { potentialAnswers.add(it) }

    potentialAnswers.shuffle()
    return potentialAnswers
}
