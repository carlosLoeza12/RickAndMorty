package com.characters.rickandmorty.ui.charactersaved

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.characters.rickandmorty.R
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.ActivityCharacterSavedBinding
import com.characters.rickandmorty.databinding.PopUpInformationBinding
import com.characters.rickandmorty.presentation.CharacterSavedViewModel
import com.characters.rickandmorty.ui.adapters.CharacterSavedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterSavedActivity : AppCompatActivity(), CharacterSavedAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterSavedViewModel>()
    private lateinit var binding: ActivityCharacterSavedBinding
    private lateinit var characterAdapter: CharacterSavedAdapter
    private var mutableListCharacter: MutableList<Character> = mutableListOf()
    private var filteredList: MutableList<Character> = mutableListOf()
    private var positionCharacterEliminated: Int = -1
    private var isFilteredList = false
    private lateinit var dialog: Dialog
    private lateinit var bindingPopUp: PopUpInformationBinding

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
        //Dialog
        dialog = Dialog(this)
        bindingPopUp = PopUpInformationBinding.inflate(LayoutInflater.from(this))
        dialog.setCancelable(false)
        dialog.setContentView(bindingPopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.itemSearch)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (mutableListCharacter.isNotEmpty()) {
                    newText?.let {
                        filterList(newText)
                        isFilteredList = true
                    }
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
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

        viewModel.isCharacterDelete.observe(this) { isDeleted ->

            if (isDeleted && isFilteredList) {
                eliminateCharacterInSearchList()
            } else if (isDeleted) {
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
        //binding
        bindingPopUp.txtInformation.text = getString(R.string.format_character_delete, character.name)

        bindingPopUp.btnYes.setOnClickListener {
            dialog.dismiss()
            viewModel.deleteCharacter(character.id)
            positionCharacterEliminated = position
//            Log.e("errorP", positionCharacterEliminated.toString())
        }

        bindingPopUp.btnNo.setOnClickListener {
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

    private fun filterList(query: String) {
        filteredList = mutableListCharacter.filter { character ->
            //the condition is that it contains in the name the letters that we pass to it
            character.name.lowercase().contains(query.lowercase())
        }.toMutableList()

        //Log.e("errorZ", filteredList.size.toString())
        characterAdapter.updateCharacterRecycler(filteredList)
    }

    private fun eliminateCharacterInSearchList(){
        mutableListCharacter.forEachIndexed { index, character ->
           if(character.id == filteredList[positionCharacterEliminated].id){
               mutableListCharacter.removeAt(index)
               filteredList.removeAt(positionCharacterEliminated)
               characterAdapter.notifyItemRemoved(positionCharacterEliminated)
           }
        }
    }

}