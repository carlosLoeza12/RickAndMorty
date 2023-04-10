package com.example.rickandmorty.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.local.Result
import com.example.rickandmorty.repository.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RickAndMortyViewModel(private val repository: RickAndMortyRepository): ViewModel() {

    private val _characterList = MutableLiveData<List<Result>>()
    val characterList = _characterList

    private val _isLoadingData = MutableLiveData<Boolean>()
    val isLoadingData = _isLoadingData


    fun getAllCharacters() {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoadingData.value = false
            val list = withContext(Dispatchers.IO) {
                repository.getAllCharacters()
            }

            if (list.result.isNotEmpty()) {
                _characterList.value = list.result
            } else {
                _isLoadingData.value = false
            }

            _isLoadingData.value = false
        }
    }


}