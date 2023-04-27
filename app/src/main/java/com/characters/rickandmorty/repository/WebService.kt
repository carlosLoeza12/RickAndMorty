package com.characters.rickandmorty.repository

import com.characters.rickandmorty.data.model.CharacterEpisode
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.CharacterLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterList

    @GET("character")
    suspend fun getAllCharactersPaging(@Query("page") page: Int): Response<CharacterList>

    @GET
    suspend fun getCharacterLocation(@Url url: String): CharacterLocation?

    @GET
    suspend fun getCharacterEpisode(@Url url: String): CharacterEpisode?
}