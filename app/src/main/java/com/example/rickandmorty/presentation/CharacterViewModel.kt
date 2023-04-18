package com.example.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CharacterRepository): ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>> = _characterList

    private val _isLoadingData = MutableLiveData<Boolean>()
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    fun getAllCharacters() {
        viewModelScope.launch {

            try {
                _isLoadingData.value = true
                val list = withContext(Dispatchers.IO) {
                    repository.getAllCharacters()
                }

                if (list.results.isNotEmpty()) {
                    _characterList.value = list.results
                }

                _isLoadingData.value = false
            } catch (e: Exception) {
                _isLoadingData.value = false
            }
        }
    }


    val list: LiveData<PagingData<Character>> =  repository.getAllCharacterPaging().cachedIn(viewModelScope)

}