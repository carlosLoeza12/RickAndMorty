package com.characters.rickandmorty.data.model


data class LocationList(
    val results: List<Location> = emptyList()
)

data class Location(
    val id: Int = 0,
    val name: String = "N/A",
    val type: String = "N/A",
    val dimension: String = "N/A"
)
