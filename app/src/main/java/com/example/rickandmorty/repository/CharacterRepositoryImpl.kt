package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.remote.CharacterDataSource
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor (private val characterDataSource: CharacterDataSource): CharacterRepository {
    override suspend fun getAllCharacters(): CharacterResponse {
        return characterDataSource.getAllCharacters()
    }
}