package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterList

    @GET("character")
    suspend fun getAllCharactersPaging(@Query("page") page: Int): Response<CharacterList>
}