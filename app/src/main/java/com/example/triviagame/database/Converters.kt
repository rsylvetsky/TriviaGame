package com.example.triviagame.database

import androidx.room.TypeConverter
import com.example.triviagame.model.TriviaStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromTriviaStatusToString(triviaStatus: TriviaStatus): String? {
        return when (triviaStatus) {
            TriviaStatus.ANSWERED_INCORRECTLY -> {
                "answered_incorrectly"
            }
            TriviaStatus.ANSWERED_CORRECTLY -> {
                "answered_correctly"
            }
            TriviaStatus.SKIPPED -> {
                "skipped"
            }
            else -> {
                "unanswered"
            }
        }
    }

    @TypeConverter
    fun fromStringToTriviaStatus(triviaStatusString: String): TriviaStatus {
        return when (triviaStatusString) {
            "answered_incorrectly" -> {
                TriviaStatus.ANSWERED_INCORRECTLY
            }
            "answered_correctly" -> {
                TriviaStatus.ANSWERED_CORRECTLY
            }
            "skipped" -> {
                TriviaStatus.SKIPPED
            }
            else -> {
                TriviaStatus.UNANSWERED
            }
        }
    }
}

