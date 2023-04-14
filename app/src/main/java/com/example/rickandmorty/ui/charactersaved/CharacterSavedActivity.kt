package com.example.rickandmorty.ui.charactersaved

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.ActivityCharacterSavedBinding
import com.example.rickandmorty.databinding.PopUpInformationBinding
import com.example.rickandmorty.presentation.CharacterSavedViewModel
import com.example.rickandmorty.ui.adapters.CharacterSavedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterSavedActivity : AppCompatActivity(), CharacterSavedAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterSavedViewModel>()
    private lateinit var binding: ActivityCharacterSavedBinding
    private lateinit var characterAdapter: CharacterSavedAdapter
    private var mutableListCharacter: MutableList<Character> = mutableListOf()
    private var positionCharacterEliminated: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterSavedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        initListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initComponents(){
        //Status bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.characters_saved)
        viewModel.getAllCharacterSaved()
    }

    private fun initListeners(){
        viewModel.characterList.observe(this) { characterList ->
            if (characterList.isNotEmpty()) {
                binding.imgEmptyData.isVisible = false
                mutableListCharacter = characterList.toMutableList()
                initRecycler(mutableListCharacter)
            }
        }

        viewModel.isLoading.observe(this){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.isCharacterDelete.observe(this){ isDeleted->
            if(isDeleted){
                updateRecycler()
            }
        }
    }

    private fun initRecycler(mutableList: MutableList<Character>){

        characterAdapter = CharacterSavedAdapter(mutableList, this@CharacterSavedActivity)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(this@CharacterSavedActivity, 2)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    override fun onCharacterClick(character: Character, position: Int) {
        showDialog(character, position)
    }

    private fun showDialog(character: Character, position: Int) {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        val binding = PopUpInformationBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //binding
        binding.txtInformation.text = getString(R.string.format_character_delete, character.name)

        binding.btnYes.setOnClickListener {
            dialog.dismiss()
            viewModel.deleteCharacter(character.id)
            positionCharacterEliminated = position
        }

        binding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateRecycler(){
        mutableListCharacter.removeAt(positionCharacterEliminated)
        characterAdapter.notifyItemRemoved(positionCharacterEliminated)
        positionCharacterEliminated = -1
        //validate if empty list
        if(mutableListCharacter.isEmpty()){
            binding.imgEmptyData.isVisible = true
        }
    }

}