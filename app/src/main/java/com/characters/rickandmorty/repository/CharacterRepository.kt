package com.characters.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.CharacterEpisode
import com.characters.rickandmorty.data.model.CharacterLocation

interface CharacterRepository {
    suspend fun getAllCharacters(): CharacterList
    suspend fun getAllCharacterSaved(): CharacterList
    suspend fun saveCharacter(character: Character): Long
    suspend fun deleteCharacter(id: Int): Int
    suspend fun getCharacter(id: Int): Character
    fun getAllCharacterPaging(): LiveData<PagingData<Character>>
    suspend fun getCharacterLocation(locationUrl: String): CharacterLocation?
    suspend fun getCharacterEpisode(episodeUrl: String): CharacterEpisode?
}