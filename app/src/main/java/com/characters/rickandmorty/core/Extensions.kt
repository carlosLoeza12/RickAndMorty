package com.characters.rickandmorty.core

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.characters.rickandmorty.R
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

fun RecyclerView.initialize(context: Context, adapter: RecyclerView.Adapter<*>){
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.setHasFixedSize(true)
    this.adapter = adapter
}

fun PagingDataAdapter<*, *>.loadStateListener(progressBar: ProgressBar, context: Context, imgEmptyData: ImageView){

    this.addLoadStateListener { loadState ->

        progressBar.isVisible = loadState.source.refresh is LoadState.Loading

        if(loadState.source.refresh is LoadState.Error){
            Toast.makeText(context, context.getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
            imgEmptyData.isVisible = true
        }
    }
}
