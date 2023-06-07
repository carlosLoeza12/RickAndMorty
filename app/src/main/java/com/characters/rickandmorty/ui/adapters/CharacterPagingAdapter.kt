package com.characters.rickandmorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.characters.rickandmorty.R
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.CharacterListItemBinding

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
        val holder = CharacterViewHolder(itemBinding, parent.context)

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

    inner class CharacterViewHolder(private val binding: CharacterListItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character){
            binding.imgCharacter.load(character.image){
                crossfade(true)
                crossfade(200)
            }
            binding.txtCharacterName.text = character.name
            binding.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_item_from_left))
        }
    }

}