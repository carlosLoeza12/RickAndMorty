package com.example.rickandmorty.data.local

import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAllCharacters(): List<CharacterEntity>

    //if is inserted, so return the row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter(characterEntity: CharacterEntity): Long

    //if is deleted, so return the rows affected
    @Query("DELETE FROM CharacterEntity WHERE id = :id")
    suspend fun deleteCharacter(id: Int): Int

    @Query("SELECT * FROM CharacterEntity WHERE id = :id LIMIT 1")
    suspend fun getCharacter(id: Int): CharacterEntity
}