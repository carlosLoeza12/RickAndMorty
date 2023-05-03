package com.characters.rickandmorty.data.model

data class EpisodeList(
    val results: List<Episode> = emptyList()
)

data class Episode(
    val id: Int  = 0,
    val name: String  = "N/A",
    val air_date: String  = "N/A",
    val episode: String  = "N/A"
)
