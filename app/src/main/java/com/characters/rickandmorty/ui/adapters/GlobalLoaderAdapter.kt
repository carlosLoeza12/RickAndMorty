package com.characters.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.characters.rickandmorty.databinding.GloablLoaderPagingBinding

class GlobalLoaderAdapter : LoadStateAdapter<GlobalLoaderAdapter.LoaderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = GloablLoaderPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoaderViewHolder(private val binding: GloablLoaderPagingBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}