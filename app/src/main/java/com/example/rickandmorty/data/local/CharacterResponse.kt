package com.example.rickandmorty.data.local

data class CharacterResponse(val result: List<Result> =  emptyList())

data class Result(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val image: String = ""
)
