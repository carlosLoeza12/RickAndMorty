package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterEpisodesViewModel @Inject constructor(repository: CharacterRepository): ViewModel() {
    val list: LiveData<PagingData<Episode>> = repository.getAllEpisodesPaging().cachedIn(viewModelScope)
}