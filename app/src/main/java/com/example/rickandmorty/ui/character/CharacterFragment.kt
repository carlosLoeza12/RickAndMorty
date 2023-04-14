package com.example.rickandmorty.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.presentation.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.ui.adapters.CharacterAdapter

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character), CharacterAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterViewModel>()
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var characterAdapter: CharacterAdapter
    private var clickPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view)

        viewModel.getAllCharacters()
        listeners()
    }

    private fun listeners(){
        viewModel.characterList.observe(viewLifecycleOwner) { list ->
            binding.imgEmptyData.isVisible = false
            initRecycler(list)
        }

        viewModel.isLoadingData.observe(viewLifecycleOwner) { isLoading->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun initRecycler(list: List<Character>){

        characterAdapter = CharacterAdapter(list, this)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(this@CharacterFragment.context, 2)
            setHasFixedSize(true)
            adapter = characterAdapter
            scrollToPosition(clickPosition)
        }
    }

    override fun onCharacterClick(character: Character, position: Int) {
        clickPosition = position
        val action = CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailsFragment(character)
        findNavController().navigate(action)
    }
}