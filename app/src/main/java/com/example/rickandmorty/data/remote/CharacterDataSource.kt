package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.repository.WebService
import javax.inject.Inject

class CharacterDataSource @Inject constructor(private val webService: WebService) {
    suspend fun getAllCharacters(): CharacterResponse{
        return webService.getAllCharacters()
    }
}