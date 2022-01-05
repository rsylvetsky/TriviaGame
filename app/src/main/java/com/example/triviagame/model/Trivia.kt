package com.example.triviagame.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Trivia(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: ArrayList<String>,
    var status: TriviaStatus
)

class TriviaWrapper(@SerializedName("response_code")  val responseCode: Int, val results: ArrayList<Trivia> )