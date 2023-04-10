package com.example.rickandmorty.repository

import com.example.rickandmorty.data.local.CharacterResponse

interface RickAndMortyRepository {
    suspend fun getAllCharacters(): CharacterResponse
}