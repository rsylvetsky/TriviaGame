package com.example.triviagame.database

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.triviagame.model.Trivia
import com.example.triviagame.model.TriviaStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(version = 1, exportSchema = false, entities = [Trivia::class])
@TypeConverters(Converters::class)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun triviaDao(): TriviaDao

    private class TriviaDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var triviaDao = database.triviaDao()

                    // Add sample words.
                    var sampleTrivia = Trivia(
                        0L,
                        "TestCategory",
                        "TestType",
                        "TestDifficult",
                        "Test Question",
                        "TestCorrectAnswer",
                        arrayListOf("IncorrectAnswer1", "IncorrectAnswer2", "IncorrrectAnswer3"),
                        TriviaStatus.UNANSWERED
                    )

                    triviaDao.insert(sampleTrivia)
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: TriviaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TriviaDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TriviaDatabase::class.java,
                    "trivia_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(TriviaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

