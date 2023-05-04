package com.characters.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.data.model.Location

interface CharacterRepository {
    suspend fun getAllCharacters(): CharacterList
    suspend fun getAllCharacterSaved(): CharacterList
    suspend fun saveCharacter(character: Character): Long
    suspend fun deleteCharacter(id: Int): Int
    suspend fun getCharacter(id: Int): Character
    fun getAllCharactersPaging(): LiveData<PagingData<Character>>
    fun getAllLocationsPaging(): LiveData<PagingData<Location>>
    fun getAllEpisodesPaging(): LiveData<PagingData<Episode>>
    suspend fun getCharacterLocation(locationUrl: String): Location?
    suspend fun getCharacterEpisode(episodeUrl: String): Episode?
}