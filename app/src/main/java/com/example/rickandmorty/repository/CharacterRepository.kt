package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterResponse

interface CharacterRepository {
    suspend fun getAllCharacters(): CharacterResponse
}