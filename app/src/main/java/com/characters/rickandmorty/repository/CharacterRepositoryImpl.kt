package com.characters.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.characters.rickandmorty.data.local.LocalCharacterDataSource
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.Location
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

    override fun getAllCharactersPaging(): LiveData<PagingData<Character>> {
        return remoteDataSource.getAllCharactersPaging()
    }

    override fun getAllLocationsPaging(): LiveData<PagingData<Location>> {
        return remoteDataSource.getAllLocationsPaging()
    }

    override fun getAllEpisodesPaging(): LiveData<PagingData<Episode>> {
        return remoteDataSource.getAllEpisodesPaging()
    }

    override suspend fun getCharacterLocation(locationUrl: String): Location? {
        return remoteDataSource.getCharacterLocation(locationUrl)
    }

    override suspend fun getCharacterEpisode(episodeUrl: String): Episode? {
        return remoteDataSource.getCharacterEpisode(episodeUrl)
    }

    override suspend fun registerToken(token: String) {
        remoteDataSource.registerToken(token)
    }

    override suspend fun sendNotification(token: String) {
        remoteDataSource.sendNotification(token)
    }
}