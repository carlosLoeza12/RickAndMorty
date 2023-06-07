package com.characters.rickandmorty.ui.characterlocations

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.characters.rickandmorty.R
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.core.loadStateListener
import com.characters.rickandmorty.databinding.FragmentCharacterLocationsBinding
import com.characters.rickandmorty.presentation.CharacterLocationViewModel
import com.characters.rickandmorty.ui.adapters.GlobalLoaderAdapter
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
                binding.linearNoData.isVisible = false
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }

        binding.txtTryAgain.setOnClickListener {
            binding.linearNoData.isVisible = false
            pagingAdapter.retry()
        }
    }

    private fun initRecycler(){
        pagingAdapter = LocationPagingAdapter()
        //show/hide progressbar
        pagingAdapter.loadStateListener(binding.progressBar, requireContext(), binding.linearNoData)
        binding.recyclerLocations.initialize(requireContext(), pagingAdapter.withLoadStateFooter(GlobalLoaderAdapter()))
    }

}