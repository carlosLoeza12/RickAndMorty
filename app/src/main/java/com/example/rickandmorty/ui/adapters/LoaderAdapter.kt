package com.example.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LoaderPagingBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoaderPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoaderViewHolder(private val binding: LoaderPagingBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}