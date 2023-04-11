package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.remote.RickAndMortyDataSource
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor (private val rickAndMortyDataSource: RickAndMortyDataSource): RickAndMortyRepository {
    override suspend fun getAllCharacters(): CharacterResponse {
        return rickAndMortyDataSource.getAllCharacters()
    }
}