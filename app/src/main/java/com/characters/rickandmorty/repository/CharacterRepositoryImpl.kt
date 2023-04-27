package com.characters.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.characters.rickandmorty.data.local.LocalCharacterDataSource
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.CharacterEpisode
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.CharacterLocation
import com.characters.rickandmorty.data.remote.RemoteCharacterDataSource
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCharacterDataSource,
    private val localDataSource: LocalCharacterDataSource
) : CharacterRepository {

    override suspend fun getAllCharacters(): CharacterList {
        return remoteDataSource.getAllCharacters()
    }

    override suspend fun getAllCharacterSaved(): CharacterList {
        return localDataSource.getAllCharactersSaved()
    }

    override suspend fun saveCharacter(character: Character): Long {
        return localDataSource.saveCharacter(character)
    }

    override suspend fun deleteCharacter(id: Int): Int {
        return localDataSource.deleteCharacter(id)
    }

    override suspend fun getCharacter(id: Int): Character {
        return localDataSource.getCharacter(id)
    }

    override fun getAllCharacterPaging(): LiveData<PagingData<Character>> {
        return remoteDataSource.getAllCharactersPaging()
    }

    override suspend fun getCharacterLocation(locationUrl: String): CharacterLocation? {
        return remoteDataSource.getCharacterLocation(locationUrl)
    }

    override suspend fun getCharacterEpisode(episodeUrl: String): CharacterEpisode? {
        return remoteDataSource.getCharacterEpisode(episodeUrl)
    }

}