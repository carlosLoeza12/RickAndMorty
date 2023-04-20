package com.characters.rickandmorty.ui.characterdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.characters.rickandmorty.R
import com.characters.rickandmorty.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.presentation.CharacterDetailViewModel

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private val viewModel by viewModels<CharacterDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)

        args.character?.let { character ->
            setCharacterData(character)
            viewModel.getCharacter(character.id)
        }

        initListeners()
    }

    private fun setCharacterData(character: Character){
        binding.imgCharacter.load(character.image)
        binding.txtName.text = character.name
        binding.txtStatus.text = character.status
        binding.txtType.text = character.type
        binding.txtSpecie.text = character.species
        binding.txtGender.text = character.gender
    }

    private fun initListeners(){

        //For save the character
        binding.imgSave.setOnClickListener {
            viewModel.saveCharacter(args.character!!)
        }

        viewModel.isCharacterSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                binding.imgSave.visibility = View.GONE
                binding.imgSaved.visibility = View.VISIBLE
                Toast.makeText(activity, R.string.detail_character_saved, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, R.string.detail_gender, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        //For delete the character
        binding.imgSaved.setOnClickListener {
            viewModel.deleteCharacter(args.character!!.id)
        }

        viewModel.isCharacterDelete.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                binding.imgSaved.visibility = View.GONE
                binding.imgSave.visibility = View.VISIBLE
                Toast.makeText(activity, R.string.detail_character_eliminated, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, R.string.detail_error_eliminate_character, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isFoundCharacter.observe(viewLifecycleOwner) { isFound ->
            if(isFound){
                binding.imgSave.visibility = View.GONE
                binding.imgSaved.visibility = View.VISIBLE
            }
        }
    }

}