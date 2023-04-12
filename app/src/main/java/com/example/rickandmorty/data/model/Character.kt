package com.example.rickandmorty.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CharacterList(val results: List<Character> =  listOf())

@Parcelize
data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val image: String = ""
): Parcelable
