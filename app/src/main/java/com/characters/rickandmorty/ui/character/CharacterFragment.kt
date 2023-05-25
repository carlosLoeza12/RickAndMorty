package com.characters.rickandmorty.ui.character

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.characters.rickandmorty.R
import com.characters.rickandmorty.core.loadStateListener
import com.characters.rickandmorty.core.validatePermission
import com.characters.rickandmorty.data.local.Permissions
import com.characters.rickandmorty.databinding.FragmentCharacterBinding
import com.characters.rickandmorty.presentation.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.characters.rickandmorty.data.model.Character
import com.characters.rickandmorty.ui.adapters.CharacterPagingAdapter
import com.characters.rickandmorty.ui.adapters.LoaderAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character), CharacterPagingAdapter.OnCharacterClickListener {

    private val viewModel by viewModels<CharacterViewModel>()
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private var clickPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // its call here because when showing the fragment again it goes through the other states
        //validate permissions and show notification
        validateNotificationPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view)

        showBottomNavigationView()
        initRecycler()
        listeners()
    }

    private fun showBottomNavigationView(){
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if(bottomNavigation?.isVisible == false){
            bottomNavigation.isVisible = true
        }
    }

    private fun listeners(){
        viewModel.list.observe(viewLifecycleOwner) { pagingData ->
            if (pagingData != null) {
                binding.imgEmptyData.isVisible = false
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun initRecycler() {
        pagingAdapter = CharacterPagingAdapter(this)

        pagingAdapter.loadStateListener(binding.progressBar, requireContext(), binding.imgEmptyData)

        binding.recyclerCharacters.apply {
            layoutManager = GridLayoutManager(this@CharacterFragment.context, 2)
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
            if (clickPosition != -1) {
                scrollToPosition(clickPosition)
                clickPosition = -1
            }
        }
    }

    override fun onCharacterClick(character: Character, position: Int) {
        val currentFragment = findNavController().currentDestination?.id ?: -1
        val destinyFragment = R.id.characterFragment
        if(currentFragment != -1 && currentFragment == destinyFragment){
            val action = CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailsFragment(character)
            findNavController().navigate(action)
        }
    }

    private fun validateNotificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val result = validatePermission(requireContext())
            if (result == Permissions.ACCEPTED) {
                sendNotification()
            }
        }else{
           sendNotification()
        }
    }

    private fun sendNotification() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            task.result?.let {
                viewModel.sendNotification(task.result.toString())
            }
        }
    }

}