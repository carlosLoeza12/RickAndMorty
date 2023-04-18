package com.example.rickandmorty.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterList
import com.example.rickandmorty.paging.CharacterPagingSource
import com.example.rickandmorty.repository.WebService
import javax.inject.Inject

class RemoteCharacterDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getAllCharacters(): CharacterList{
        return webService.getAllCharacters()
    }

    fun getAllCharactersPaging(): LiveData<PagingData<Character>>{
        return Pager(
            config = PagingConfig(pageSize = 1, maxSize = 1000),
            pagingSourceFactory = { CharacterPagingSource(webService) }
        ).liveData
    }
}