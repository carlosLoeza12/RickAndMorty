package com.characters.rickandmorty.data.local

import com.characters.rickandmorty.core.toCharacter
import com.characters.rickandmorty.core.toCharacterEntity
import com.characters.rickandmorty.core.toCharacterList
import com.characters.rickandmorty.data.model.CharacterList
import javax.inject.Inject
import com.characters.rickandmorty.data.model.Character

class LocalCharacterDataSource @Inject constructor(private val dao: CharacterDao) {

    suspend fun getAllCharactersSaved(): CharacterList {
        return dao.getAllCharactersSaved().toCharacterList()
    }

    suspend fun saveCharacter(character: Character): Long {
        return dao.saveCharacter(character.toCharacterEntity())
    }

    suspend fun deleteCharacter(id: Int): Int {
        return dao.deleteCharacter(id)
    }

    suspend fun getCharacter(id: Int): Character {
        return dao.getCharacter(id).toCharacter()
    }
}