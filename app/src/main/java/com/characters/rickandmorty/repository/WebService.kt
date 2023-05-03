package com.characters.rickandmorty.repository

import com.characters.rickandmorty.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterList

    @GET("character")
    suspend fun getAllCharactersPaging(@Query("page") page: Int): Response<CharacterList>

    @GET
    suspend fun getCharacterLocation(@Url url: String): Location?

    @GET
    suspend fun getCharacterEpisode(@Url url: String): Episode?

    @GET("location")
    suspend fun getAllLocationsPaging(@Query("page")page: Int): Response<LocationList>

    @GET("episode")
    suspend fun getAllEpisodesPaging(@Query("page")page: Int): Response<EpisodeList>
}