package com.example.rickandmorty.data.model

data class CharacterResponse(val results: List<Character> =  listOf())

data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val image: String = ""
)
