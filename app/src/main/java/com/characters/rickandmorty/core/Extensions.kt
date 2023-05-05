package com.characters.rickandmorty.core

import android.app.Dialog
import android.view.View
import com.characters.rickandmorty.data.local.CharacterEntity
import com.characters.rickandmorty.data.model.CharacterList
import com.characters.rickandmorty.data.model.Character

//Room
fun List<CharacterEntity>.toCharacterList(): CharacterList{
    val resultList: MutableList<Character> = mutableListOf()

    this.forEach { characterEntity ->
        resultList.add(characterEntity.toCharacter())
    }
    return CharacterList(resultList)
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        this.id,
        this.name,
        this.status,
        this.species,
        this.type,
        this.gender,
        this.image
    )
}


fun Character.toCharacterEntity(): CharacterEntity{
   return CharacterEntity(
        this.id,
        this.name,
        this.status,
        this.species,
        this.type,
        this.gender,
        this.image
    )
}


fun Dialog.initialize(view: View, isCancelable: Boolean): Dialog {
    this.setContentView(view)
    this.setCancelable(isCancelable)
    this.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return this
}
