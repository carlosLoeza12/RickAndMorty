package com.example.rickandmorty.repository

import com.example.rickandmorty.data.local.CharacterResponse

class RickAndMortyRepositoryImpl(private val webService: WebService ): RickAndMortyRepository {
    override suspend fun getAllCharacters(): CharacterResponse {
        return webService.getAllCharacters()
    }
}