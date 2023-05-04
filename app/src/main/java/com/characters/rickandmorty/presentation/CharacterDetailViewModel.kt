package com.characters.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.data.model.Location
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

    private val _isLoadingLocation = MutableLiveData<Boolean>()
    val isLoadingLocation: LiveData<Boolean> = _isLoadingLocation

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private val _isLoadingEpisode = MutableLiveData<Boolean>()
    val isLoadingEpisode: LiveData<Boolean> = _isLoadingEpisode

    private val _episode = MutableLiveData<Episode>()
    val episode: LiveData<Episode> = _episode

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

    fun getCharacterLocation(locationUrl: String){
        viewModelScope.launch {
            _isLoadingLocation.value = true

            try {
                val locationResult = withContext(Dispatchers.IO){
                    repository.getCharacterLocation(locationUrl)
                }

                locationResult?.let {
                    _location.value = locationResult!!
                }

                _isLoadingLocation.value = false
            }catch (e: Exception){
                _isLoadingLocation.value = false
            }

        }
    }

    fun getCharacterEpisode(episodeUrl: String){
        viewModelScope.launch {
            _isLoadingEpisode.value = true

            try {
                val episodeResult = withContext(Dispatchers.IO){
                    repository.getCharacterEpisode(episodeUrl)
                }

                episodeResult?.let {
                    _episode.value = episodeResult!!
                }

                _isLoadingEpisode.value = false
            }catch (e: Exception){
                _isLoadingEpisode.value = false
            }

        }
    }
}