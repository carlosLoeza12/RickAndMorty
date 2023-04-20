package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class CharacterDetailViewModel @Inject constructor (private val repository: CharacterRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCharacterSaved = MutableLiveData<Boolean>()
    val isCharacterSaved: LiveData<Boolean> = _isCharacterSaved

    private val _isCharacterDelete = MutableLiveData<Boolean>()
    val isCharacterDelete: LiveData<Boolean> = _isCharacterDelete

    private val _isFoundCharacter = MutableLiveData<Boolean>()
    val isFoundCharacter: LiveData<Boolean> = _isFoundCharacter

    fun getCharacter(id: Int) {
        //this method validate if the character is saved
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val character = withContext(Dispatchers.IO) {
                    repository.getCharacter(id)
                }

                if (!character.equals(null)) {
                    _isFoundCharacter.value = true
                }

                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    fun saveCharacter(character: Character) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rowId = withContext(Dispatchers.IO) {
                    repository.saveCharacter(character)
                }

                if (rowId.toInt() != 0) {
                    _isCharacterSaved.value = true
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _isCharacterSaved.value = false
            }
        }
    }

    fun deleteCharacter(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {

                val resultRowAffected = withContext(Dispatchers.IO) {
                    repository.deleteCharacter(id)
                }

                if (resultRowAffected != 0) {
                    _isCharacterDelete.value = true
                }
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
                _isCharacterDelete.value = false
            }
        }
    }
}