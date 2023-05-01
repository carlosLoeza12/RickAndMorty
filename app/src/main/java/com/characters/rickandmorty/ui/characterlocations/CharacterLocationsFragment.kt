package com.characters.rickandmorty.ui.characterlocations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.characters.rickandmorty.R
import com.characters.rickandmorty.databinding.FragmentCharacterLocationsBinding

class CharacterLocationsFragment : Fragment(R.layout.fragment_character_locations) {

    private lateinit var binding: FragmentCharacterLocationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterLocationsBinding.bind(view)
    }

}