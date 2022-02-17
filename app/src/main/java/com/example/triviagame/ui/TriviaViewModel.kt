package com.example.triviagame.ui

import androidx.lifecycle.*
import com.example.triviagame.model.Trivia
import com.example.triviagame.repo.TriviaRepository
import kotlinx.coroutines.launch

class TriviaViewModel(private val repository: TriviaRepository) : ViewModel() {

    init {
        repository.loadItems()
    }

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTrivia: LiveData<List<Trivia>> = repository.allTrivia

    val nextUnansweredTrivia: LiveData<Trivia> = repository.nextUnansweredTrivia

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(trivia: Trivia) = viewModelScope.launch {
        repository.insert(trivia)
    }


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