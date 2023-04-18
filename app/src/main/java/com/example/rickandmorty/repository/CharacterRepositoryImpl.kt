package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.rickandmorty.data.local.LocalCharacterDataSource
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterList
import com.example.rickandmorty.data.remote.RemoteCharacterDataSource
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

}