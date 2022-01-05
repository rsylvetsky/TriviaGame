package com.example.triviagame.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.triviagame.model.Trivia
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDao {
    @Insert(onConflict = REPLACE)
    fun insert(trivia: Trivia)

    @Delete
    fun delete(trivia: Trivia)

    @Query("SELECT * FROM trivia")
    fun getAllTrivia(): Flow<List<Trivia>>

    @Query("SELECT * FROM trivia WHERE status = 'UNANSWERED' ORDER BY RANDOM() LIMIT 1")
    fun getNextUnansweredTrivia() : Flow<Trivia>
}