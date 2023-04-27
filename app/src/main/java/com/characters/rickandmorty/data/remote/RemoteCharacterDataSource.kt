package com.characters.rickandmorty.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.CharacterEpisode
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.CharacterLocation
import com.characters.rickandmorty.paging.CharacterPagingSource
import com.characters.rickandmorty.repository.WebService
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

    suspend fun getCharacterLocation(locationUrl: String): CharacterLocation? {
        return webService.getCharacterLocation(locationUrl)
    }

    suspend fun getCharacterEpisode(episodeUrl: String) : CharacterEpisode? {
        return webService.getCharacterEpisode(episodeUrl)
    }
}