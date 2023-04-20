package com.characters.rickandmorty.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo("status")
    val status: String = "",
    @ColumnInfo("species")
    val species: String = "",
    @ColumnInfo("type")
    val type: String = "",
    @ColumnInfo("gender")
    val gender: String = "",
    @ColumnInfo("image")
    val image: String = ""
)
