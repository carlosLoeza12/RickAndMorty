package com.example.rickandmorty.data.local

import com.example.rickandmorty.core.toCharacter
import com.example.rickandmorty.core.toCharacterEntity
import com.example.rickandmorty.core.toCharacterList
import com.example.rickandmorty.data.model.CharacterList
import javax.inject.Inject
import com.example.rickandmorty.data.model.Character

class LocalCharacterDataSource @Inject constructor(private val dao: CharacterDao) {

    suspend fun getAllCharacters(): CharacterList {
        return dao.getAllCharacters().toCharacterList()
    }

    suspend fun saveCharacter(character: Character): Long {
        return dao.saveCharacter(character.toCharacterEntity())
    }

    suspend fun deleteCharacter(id: Int): Int{
        return  dao.deleteCharacter(id)
    }

    suspend fun getCharacter(id: Int): Character {
        return dao.getCharacter(id).toCharacter()
    }
}