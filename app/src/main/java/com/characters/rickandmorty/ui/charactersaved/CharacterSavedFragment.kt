package com.characters.rickandmorty.ui.charactersaved

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.characters.rickandmorty.R
import com.characters.rickandmorty.core.NetworkResult
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.databinding.FragmentCharacterSavedBinding
import com.characters.rickandmorty.databinding.PopUpInformationYesNoBinding
import com.characters.rickandmorty.presentation.CharacterSavedViewModel
import com.characters.rickandmorty.ui.adapters.CharacterSavedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterSavedFragment : Fragment(R.layout.fragment_character_saved),
    CharacterSavedAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterSavedViewModel>()
    private lateinit var binding: FragmentCharacterSavedBinding
    private lateinit var characterAdapter: CharacterSavedAdapter
    private var mutableListCharacter: MutableList<Character> = mutableListOf()
    private var filteredList: MutableList<Character> = mutableListOf()
    private var isFilteredList = false
    private var positionCharacterEliminated: Int = -1
    private lateinit var dialog: Dialog
    private lateinit var bindingPopUp: PopUpInformationYesNoBinding
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
        bindingPopUp = PopUpInformationYesNoBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.initialize(bindingPopUp.root, false)

        //adapter and Recycler
        characterAdapter = CharacterSavedAdapter(mutableListCharacter, this)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun initListeners(){

        viewModel.isCharacterEliminated.observe(viewLifecycleOwner){ isEliminated ->

            when(isEliminated){
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    if (isEliminated.data == true){
                        viewModel.getAllCharacterSaved()
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.networkResult.observe(viewLifecycleOwner){result ->
            when(result){

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false

                    if(!result.data.isNullOrEmpty()){
                        binding.imgEmptyData.isVisible = false
                        mutableListCharacter = result.data.toMutableList()

                        if (isFilteredList) {
                            eliminateCharacterInSearchList()
                        } else {
                            characterAdapter.updateCharacterRecycler(mutableListCharacter)
                            firstLoad = true
                        }
                    }else{
                        if (firstLoad) {
                            binding.imgEmptyData.isVisible = true
                            mutableListCharacter = mutableListOf()
                            characterAdapter.updateCharacterRecycler(mutableListCharacter)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
                }
            }
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
            //makeCharacterDelete(character.id)
            viewModel.deleteCharacter(character.id)
        }

        bindingPopUp.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}