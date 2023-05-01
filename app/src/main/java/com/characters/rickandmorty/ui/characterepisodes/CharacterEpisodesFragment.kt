package com.characters.rickandmorty.ui.characterepisodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.characters.rickandmorty.R
import com.characters.rickandmorty.databinding.FragmentCharacterEpisodesBinding

class CharacterEpisodesFragment : Fragment(R.layout.fragment_character_episodes) {

    private lateinit var binding: FragmentCharacterEpisodesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterEpisodesBinding.bind(view)
    }

}