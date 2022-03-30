package com.example.triviagame.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.triviagame.api.ApiClient
import com.example.triviagame.api.TriviaApi
import com.example.triviagame.database.TriviaDao
import com.example.triviagame.model.Trivia
import com.example.triviagame.model.TriviaWrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Future

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TriviaRepository(private val triviaDao: TriviaDao) {


    val triviaApi: TriviaApi = ApiClient().getClient().create(TriviaApi::class.java)

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTrivia: LiveData<List<Trivia>> = triviaDao.getAllTrivia()

    val nextUnansweredTrivia: LiveData<Trivia> = triviaDao.getNextUnansweredTrivia()

    //TODO add in network failure stuff use CoRoutines
    //retrofit rxjava subscribeOnIO thread
    // CoRoutines KOtlin thing
    fun loadItems() {
        // get from network
        triviaApi.getTrivia().enqueue(object : Callback<TriviaWrapper> {
            override fun onResponse(
                call: Call<TriviaWrapper>,
                response: Response<TriviaWrapper>
            ) {

                if (response.isSuccessful) {

                    // When data is available, populate LiveData
                    response.body()?.results?.forEach { trivia -> triviaDao.insert(trivia) }
                }
            }

            override fun onFailure(call: Call<TriviaWrapper>, t: Throwable) {
                t.printStackTrace()
            }
        })
        //load from local
//            val loadFromLocal = triviaDao.getAllItem()
//
//            emitSource(loadFromLocal)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trivia: Trivia) {
        triviaDao.insert(trivia)
    }
}