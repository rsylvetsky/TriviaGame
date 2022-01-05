package com.example.triviagame.repo

import androidx.annotation.WorkerThread
import com.example.triviagame.database.TriviaDao
import com.example.triviagame.model.Trivia
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TriviaRepository(private val triviaDao: TriviaDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTrivia: Flow<List<Trivia>> = triviaDao.getAllTrivia()

    val nextUnansweredTrivia: Flow<Trivia> = triviaDao.getNextUnansweredTrivia()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trivia: Trivia) {
        triviaDao.insert(trivia)
    }


}