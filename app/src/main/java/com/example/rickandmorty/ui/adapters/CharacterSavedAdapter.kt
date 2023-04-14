package com.example.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.CharacterListItemBinding

class CharacterSavedAdapter(
    private val mutableList: MutableList<Character>,
    private val onCharacterClickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharacterSavedAdapter.ViewHolderCharacter>() {

    interface OnCharacterClickListener{
        fun onCharacterClick(character: Character, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCharacter {
        val itemBinding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolderCharacter(itemBinding)

        itemBinding.imgDelete.setOnClickListener {
            val position = holder.adapterPosition.takeIf {
                it != -1
            } ?: return@setOnClickListener
            onCharacterClickListener.onCharacterClick(mutableList[position], position)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolderCharacter, position: Int) {
        holder.bind(mutableList[position])
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolderCharacter(private val binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character){
            binding.imgCharacter.load(character.image)
            binding.txtCharacterName.text = character.name
            binding.imgDelete.visibility = View.VISIBLE
        }
    }
}