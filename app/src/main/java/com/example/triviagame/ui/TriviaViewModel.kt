package com.example.triviagame.ui

import androidx.lifecycle.*
import com.example.triviagame.model.Trivia
import com.example.triviagame.repo.TriviaRepository
import kotlinx.coroutines.launch

class TriviaViewModel(private val repository: TriviaRepository) : ViewModel() {
    // VS activity will listen to
    //add methods that will publish to this VS
    private var trivia: LiveData<Trivia>
    var viewState : MutableLiveData<TriviaViewState> = MutableLiveData()

    init {
        repository.loadItems()
        trivia = repository.nextUnansweredTrivia
        initializeViewState(trivia)
    }

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTrivia: LiveData<List<Trivia>> = repository.allTrivia




    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(trivia: Trivia) = viewModelScope.launch {
        repository.insert(trivia)
    }

    private fun initializeViewState(trivia: LiveData<Trivia>) {
//transform livedata trivia to livedata of VS
       Transformations.map(trivia) {triv ->
           val allAnswers = scrambleQuestions(triv)
           TriviaViewState(triv.question, allAnswers, triv.correctAnswer, AnswerState.Unanswered)
       }.observeForever{
           viewState.value = it
       }
        //initial vs says, when we get the trivia ld from db, scramble the questions. then once we do this processing, update the vs
    }

    fun submitAnswer(index: Int) {
        //whenever you want to update the vs you can use this and pass whatever you want
        if (viewState.value!!.answers[index] == viewState.value!!.correctAnswer) {
            viewState.value = viewState.value!!.copy(
                answerState = AnswerState.AnsweredCorrectly
            )

        } else {
            viewState.value = viewState.value!!.copy(
                answerState = AnswerState.AnsweredIncorrectly
            )
        }


    }

    //next question should do something to the VM, get the data from the repo and update the vs
    //fetch a new question object through the db, do something similar to the viewstate,
}

private fun scrambleQuestions(trivia: Trivia) : MutableList<String> {
    var potentialAnswers: MutableList<String> = mutableListOf()

    potentialAnswers.add(trivia.correctAnswer)
    trivia.incorrectAnswers.map{ potentialAnswers.add(it)}

    potentialAnswers.shuffle()
    return potentialAnswers
}

class TriviaViewModelFactory(private val repository: TriviaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TriviaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TriviaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}