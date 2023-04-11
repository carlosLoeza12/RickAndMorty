package com.example.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Result
import com.example.rickandmorty.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(private val repository: RickAndMortyRepository): ViewModel() {

    private val _characterList = MutableLiveData<List<Result>>()
    val characterList: LiveData<List<Result>> = _characterList

    private val _isLoadingData = MutableLiveData<Boolean>()
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    fun getAllCharacters() {
        viewModelScope.launch {

            _isLoadingData.value = true
            val list = withContext(Dispatchers.IO) {
                repository.getAllCharacters()
            }

            if (list.results.isNotEmpty()) _characterList.value = list.results else _isLoadingData.value = false
        }
    }

}