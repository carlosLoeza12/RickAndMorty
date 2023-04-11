package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterResponse

interface RickAndMortyRepository {
    suspend fun getAllCharacters(): CharacterResponse
}