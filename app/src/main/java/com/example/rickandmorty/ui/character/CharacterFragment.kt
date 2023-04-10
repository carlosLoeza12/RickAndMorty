package com.example.rickandmorty.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment(R.layout.fragment_character) {

    private lateinit var binding: FragmentCharacterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view);
    }

}