package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterResponse
import retrofit2.http.GET

interface WebService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterResponse
}