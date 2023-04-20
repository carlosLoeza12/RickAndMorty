package com.characters.rickandmorty.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.CharacterListItemBinding

class CharacterSavedAdapter(
    private var characterList: List<Character>,
    private val onCharacterClickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharacterSavedAdapter.ViewHolderCharacter>() {

    interface OnCharacterClickListener{
        fun onCharacterClick(character: Character, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCharacter {
        val itemBinding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolderCharacter(itemBinding)

        itemBinding.imgDelete.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != -1
            } ?: return@setOnClickListener
            onCharacterClickListener.onCharacterClick(characterList[position], position)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolderCharacter, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size

    inner class ViewHolderCharacter(private val binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character){
            binding.imgCharacter.load(character.image)
            binding.txtCharacterName.text = character.name
            binding.imgDelete.visibility = View.VISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacterRecycler(list: List<Character>){
        this.characterList = list
        notifyDataSetChanged()
    }
}