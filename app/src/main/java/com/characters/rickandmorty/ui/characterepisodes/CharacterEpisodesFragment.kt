package com.characters.rickandmorty.ui.characterepisodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.characters.rickandmorty.R
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

        pagingAdapter.addLoadStateListener { loadState ->

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if(loadState.source.refresh is LoadState.Error){
                Toast.makeText(requireContext(), getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
                binding.imgEmptyData.isVisible = true
            }
        }

        binding.recyclerEpisodes.apply {
            layoutManager = LinearLayoutManager(
                this@CharacterEpisodesFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            setHasFixedSize(true)
            adapter = pagingAdapter
        }

    }

}