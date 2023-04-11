package com.example.rickandmorty.ui.character

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.presentation.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val viewModel by viewModels<RickAndMortyViewModel>()
    private lateinit var binding: FragmentCharacterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view)

        viewModel.getAllCharacters()

        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            Log.e("result", it.toString())
        })
    }

}