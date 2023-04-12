package com.example.rickandmorty.ui.characterdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.rickandmorty.data.model.Character

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args by navArgs<CharacterDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        args.character?.let { character ->
            setCharacterData(character)
        }
    }

    private fun setCharacterData(character: Character){
        binding.imgCharacter.load(character.image)
        binding.txtName.text = character.name
        binding.txtStatus.text = character.status
        binding.txtType.text = character.type
        binding.txtSpecie.text = character.species
        binding.txtGender.text = character.gender
    }

}