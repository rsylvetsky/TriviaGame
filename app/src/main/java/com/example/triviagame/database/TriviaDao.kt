package com.example.triviagame.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.triviagame.model.Trivia
import com.example.triviagame.model.TriviaStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDao {
    @Insert(onConflict = REPLACE)
    fun insert(trivia: Trivia)

    @Update(entity = Trivia::class)
    fun update(trivia: Trivia)

    @Query("UPDATE trivia SET status = :triviaStatus WHERE id = :triviaId")
    fun updateStatus(triviaId: Long, triviaStatus: TriviaStatus)

    @Delete
    fun delete(trivia: Trivia)

    @Query("SELECT * FROM trivia")
    fun getAllTrivia(): LiveData<List<Trivia>>

    @Query("SELECT * FROM trivia WHERE status = 'unanswered' ORDER BY RANDOM() LIMIT 1")
    suspend fun getNextUnansweredTrivia() : Trivia
}