package com.example.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.CharacterListItemBinding

class CharacterPagingAdapter(private val onCharacterClickListener: OnCharacterClickListener) :
    PagingDataAdapter<Character, CharacterPagingAdapter.CharacterViewHolder>(diffCallback) {

    interface OnCharacterClickListener{
        fun onCharacterClick(character: Character, position: Int)
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Character>(){
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
              return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemBinding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CharacterViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != -1
            } ?: return@setOnClickListener

            val item = getItem(position)

            if (item != null) {
                onCharacterClickListener.onCharacterClick(item, position)
            }
        }

        return holder
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }

    }

    inner class CharacterViewHolder(private val binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character){
            binding.imgCharacter.load(character.image)
            binding.txtCharacterName.text = character.name
        }
    }

}