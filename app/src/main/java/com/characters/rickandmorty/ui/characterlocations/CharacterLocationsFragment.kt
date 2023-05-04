package com.characters.rickandmorty.ui.characterlocations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.characters.rickandmorty.R
import com.characters.rickandmorty.databinding.FragmentCharacterLocationsBinding
import com.characters.rickandmorty.presentation.CharacterLocationViewModel
import com.characters.rickandmorty.ui.adapters.LocationPagingAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterLocationsFragment : Fragment(R.layout.fragment_character_locations) {

    private lateinit var binding: FragmentCharacterLocationsBinding
    private val viewModel by viewModels<CharacterLocationViewModel>()
    private lateinit var pagingAdapter: LocationPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterLocationsBinding.bind(view)

        initRecycler()

        viewModel.locationResult.observe(viewLifecycleOwner) { pagingData ->
            if (pagingData != null) {
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun initRecycler(){
        pagingAdapter = LocationPagingAdapter()

        //show/hide progressbar
        pagingAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if(loadState.source.refresh is LoadState.Error){
                Toast.makeText(requireContext(), getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
                binding.imgEmptyData.isVisible = true
            }
        }

        binding.recyclerLocations.apply {
            layoutManager = LinearLayoutManager(
                this@CharacterLocationsFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            setHasFixedSize(true)
            adapter = pagingAdapter
        }
    }

}