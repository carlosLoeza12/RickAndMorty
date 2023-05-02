package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterSavedViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>> = _characterList

    fun getAllCharacterSaved() {
        viewModelScope.launch {

            _isLoading.value = true
            try {

                val characterList = withContext(Dispatchers.IO) {
                    repository.getAllCharacterSaved()
                }

                _characterList.value = characterList.results
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
            }

        }
    }

//    fun deleteCharacter(id: Int) {
//        viewModelScope.launch(Dispatchers.Main) {
//            _isLoading.value = true
//            try {
//
//                val resultRowAffected = withContext(Dispatchers.IO) {
//                    repository.deleteCharacter(id)
//                }
//
//                if (resultRowAffected != 0) {
//                    _isCharacterDelete.value = true
//                }
//                _isLoading.value = false
//
//            } catch (e: Exception) {
//                _isLoading.value = false
//                _isCharacterDelete.value = false
//            }
//        }
//    }

    fun deleteCharacter(characterId: Int): Boolean {
        val result = runBlocking(Dispatchers.Default) {
            try {
                _isLoading.postValue(true)
                val resultRowAffected = withContext(Dispatchers.IO) { repository.deleteCharacter(characterId) }
                _isLoading.postValue(false)
                resultRowAffected != 0
            } catch (e: Exception) {
                false
            }
        }
        return result
    }
}