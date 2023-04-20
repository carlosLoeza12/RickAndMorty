package com.characters.rickandmorty.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.CharacterListItemBinding

class CharacterAdapter(
    private val list: List<Character>,
    private val onCharacterClickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharacterAdapter.ViewHolderCharacter>() {

    interface OnCharacterClickListener{
        fun onCharacterClick(character: Character, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCharacter {
        val itemBinding = CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolderCharacter(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                it != -1
            } ?: return@setOnClickListener
            onCharacterClickListener.onCharacterClick(list[position], position)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolderCharacter, position: Int) {
      holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolderCharacter(private val binding: CharacterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character){
            binding.imgCharacter.load(character.image)
            binding.txtCharacterName.text = character.name
        }

    }
}