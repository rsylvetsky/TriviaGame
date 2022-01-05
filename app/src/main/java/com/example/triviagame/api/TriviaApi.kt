package com.example.triviagame.api

import com.example.triviagame.model.TriviaWrapper
import retrofit2.Call
import retrofit2.http.GET

interface TriviaApi {

    @GET("api.php?amount=100&category=12&difficulty=easy&type=multiple")
    fun getTrivia(): Call<TriviaWrapper>

}