package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.CharacterList
import com.example.rickandmorty.repository.WebService
import javax.inject.Inject

class RemoteCharacterDataSource @Inject constructor(private val webService: WebService) {
    suspend fun getAllCharacters(): CharacterList{
        return webService.getAllCharacters()
    }
}