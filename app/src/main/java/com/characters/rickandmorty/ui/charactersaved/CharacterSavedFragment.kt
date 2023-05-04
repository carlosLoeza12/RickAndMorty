package com.characters.rickandmorty.ui.charactersaved

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.characters.rickandmorty.R
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.FragmentCharacterSavedBinding
import com.characters.rickandmorty.databinding.PopUpInformationBinding
import com.characters.rickandmorty.presentation.CharacterSavedViewModel
import com.characters.rickandmorty.ui.adapters.CharacterSavedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterSavedFragment : Fragment(R.layout.fragment_character_saved),  CharacterSavedAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterSavedViewModel>()
    private lateinit var binding: FragmentCharacterSavedBinding
    private lateinit var characterAdapter: CharacterSavedAdapter
    private var mutableListCharacter: MutableList<Character> = mutableListOf()
    private var filteredList: MutableList<Character> = mutableListOf()
    private var isFilteredList = false
    private var positionCharacterEliminated: Int = -1
    private lateinit var dialog: Dialog
    private lateinit var bindingPopUp: PopUpInformationBinding
    private var firstLoad = false
    private lateinit var menuHost: MenuHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterSavedBinding.bind(view)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        viewModel.getAllCharacterSaved()

        //menu
        menuHost = requireActivity()
        showSearchMenu()
        //Dialog
        dialog = Dialog(requireContext())
        bindingPopUp = PopUpInformationBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setCancelable(false)
        dialog.setContentView(bindingPopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //adapter and Recycler
        characterAdapter = CharacterSavedAdapter(mutableListCharacter, this)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun initListeners(){
        viewModel.characterList.observe(viewLifecycleOwner) { characterList ->
            if (characterList.isNotEmpty()) {

                binding.imgEmptyData.isVisible = false
                mutableListCharacter = characterList.toMutableList()

                if (isFilteredList) {
                    eliminateCharacterInSearchList()
                } else {
                    characterAdapter.updateCharacterRecycler(mutableListCharacter)
                    firstLoad = true
                }

            } else {
                if (firstLoad) {
                    binding.imgEmptyData.isVisible = true
                    mutableListCharacter = mutableListOf()
                    characterAdapter.updateCharacterRecycler(mutableListCharacter)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }

    }

    private fun eliminateCharacterInSearchList(){
        filteredList.removeAt(positionCharacterEliminated)
        characterAdapter.notifyItemRemoved(positionCharacterEliminated)
    }

    private fun showSearchMenu(){
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.itemSearch)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if(mutableListCharacter.isNotEmpty()){
                            isFilteredList = false
                            newText?.let {
                                filterList(newText)
                                isFilteredList = true
                            }
                        }
                        return true
                    }
                })

            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun filterList(query: String) {
        filteredList = mutableListCharacter.filter { character ->
            //the condition is that it contains in the name the letters that we pass to it
            character.name.lowercase().contains(query.lowercase())
        }.toMutableList()

        characterAdapter.updateCharacterRecycler(filteredList)
    }

    override fun onCharacterClick(character: Character, position: Int) {
        showDialog(character, position)
    }

    private fun showDialog(character: Character, position: Int) {
        //binding
        bindingPopUp.txtInformation.text = getString(R.string.format_character_delete, character.name)

        bindingPopUp.btnYes.setOnClickListener {
            dialog.dismiss()
            positionCharacterEliminated = position
            makeCharacterDelete(character.id)
        }

        bindingPopUp.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun makeCharacterDelete(characterId: Int){
        val result = viewModel.deleteCharacter(characterId)
        if(result){
          viewModel.getAllCharacterSaved()
        }
    }
}