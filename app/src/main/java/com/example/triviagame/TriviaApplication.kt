package com.example.triviagame

import android.app.Application
import com.example.triviagame.database.TriviaDatabase
import com.example.triviagame.repo.TriviaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TriviaApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TriviaDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TriviaRepository(database.triviaDao()) }
}