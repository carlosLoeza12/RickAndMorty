package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characters.rickandmorty.core.NetworkResult
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CharacterSavedViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    private val _isCharacterEliminated = MutableLiveData<NetworkResult<Boolean>>()
    val isCharacterEliminated: LiveData<NetworkResult<Boolean>> = _isCharacterEliminated

    private val _networkResult =  MutableLiveData<NetworkResult<List<Character>>>()
    val networkResult: LiveData<NetworkResult<List<Character>>> = _networkResult


    fun getAllCharacterSaved() {
        viewModelScope.launch {

            try {

                _networkResult.value = NetworkResult.Loading()
                val characterList = withContext(Dispatchers.IO) {
                    repository.getAllCharacterSaved()
                }

                _networkResult.value = NetworkResult.Success(characterList.results)

            } catch (e: Exception) {
                _networkResult.value = NetworkResult.Error(e.toString())
            }

        }
    }

    fun deleteCharacter(characterId: Int){
//        val result = runBlocking(Dispatchers.Default) {
//            try {
//                _isLoading.postValue(true)
//                val resultRowAffected = withContext(Dispatchers.IO) { repository.deleteCharacter(characterId) }
//                _isLoading.postValue(false)
//                resultRowAffected != 0
//            } catch (e: Exception) {
//                false
//            }
//        }
//        return result

        viewModelScope.launch {
            try {
                _isCharacterEliminated.value = NetworkResult.Loading()
                //val result = async(Dispatchers.IO) { repository.deleteCharacter(characterId) }
                val result = withContext(Dispatchers.IO){repository.deleteCharacter(characterId)}
                _isCharacterEliminated.value = NetworkResult.Success(result != 0)
            } catch (e: Exception) {
                _isCharacterEliminated.value = NetworkResult.Error(e.toString())
            }
        }
    }
}