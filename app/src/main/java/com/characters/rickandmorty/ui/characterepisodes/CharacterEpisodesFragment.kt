package com.characters.rickandmorty.ui.characterepisodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.characters.rickandmorty.R
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.core.loadStateListener
import com.characters.rickandmorty.databinding.FragmentCharacterEpisodesBinding
import com.characters.rickandmorty.presentation.CharacterEpisodesViewModel
import com.characters.rickandmorty.ui.adapters.EpisodePagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterEpisodesFragment : Fragment(R.layout.fragment_character_episodes) {

    private lateinit var binding: FragmentCharacterEpisodesBinding
    private val viewModel by viewModels<CharacterEpisodesViewModel>()
    private lateinit var pagingAdapter: EpisodePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterEpisodesBinding.bind(view)

        initRecycler()

        viewModel.list.observe(viewLifecycleOwner){ pagingData ->
            if(pagingData != null){
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun initRecycler(){
        pagingAdapter = EpisodePagingAdapter()
        pagingAdapter.loadStateListener(binding.progressBar, requireContext(), binding.imgEmptyData)
        binding.recyclerEpisodes.initialize(requireContext(), pagingAdapter)
    }

}