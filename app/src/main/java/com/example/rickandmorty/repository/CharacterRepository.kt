package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.CharacterList
import com.example.rickandmorty.data.model.Character

interface CharacterRepository {
    suspend fun getAllCharacters(): CharacterList
    suspend fun saveCharacter(character: Character): Long
    suspend fun deleteCharacter(id: Int): Int
    suspend fun getCharacter(id: Int): Character
}