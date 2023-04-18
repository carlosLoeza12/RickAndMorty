package com.example.rickandmorty.ui.character

import android.os.Bundle
import android.util.Log
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
import com.example.rickandmorty.ui.adapters.CharacterPagingAdapter
import com.example.rickandmorty.ui.adapters.LoaderAdapter

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character), CharacterPagingAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterViewModel>()
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private var clickPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view)

        initRecycler()
        listeners()
    }

    private fun listeners(){

        viewModel.list.observe(viewLifecycleOwner) { pagingData ->
            if (pagingData != null) {
                binding.imgEmptyData.isVisible = false
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun initRecycler() {
        pagingAdapter = CharacterPagingAdapter(this)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(this@CharacterFragment.context, 2)
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
            scrollToPosition(clickPosition)
        }
    }

    override fun onCharacterClick(character: Character, position: Int) {
        clickPosition = position
        val action = CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailsFragment(character)
        findNavController().navigate(action)
    }
}