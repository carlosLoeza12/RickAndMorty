package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.characters.rickandmorty.data.model.Location
import com.characters.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterLocationViewModel @Inject constructor(repository: CharacterRepository): ViewModel() {
    val locationResult: LiveData<PagingData<Location>> = repository.getAllLocationsPaging().cachedIn(viewModelScope)
}