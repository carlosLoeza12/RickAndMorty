package com.characters.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.characters.rickandmorty.databinding.ActivityMainBinding
import com.characters.rickandmorty.ui.charactersaved.CharacterSavedActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController =  navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
        //set up action bar with nav controller
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.navSaved ->{
//                val activityNavigator = ActivityNavigator( this)
//                activityNavigator.navigate(
//                    activityNavigator.createDestination().setIntent(
//                        Intent(
//                            this,
//                            CharacterSavedActivity::class.java
//                        )
//                    ), null, null, null
//                )
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private fun appConfigs(){
//
//        Log.e("aaa", BuildConfig.BUILD_TYPE)
//
//        if(BuildConfig.BUILD_TYPE == "release"){
//
//        }
//
//        if(BuildConfig.FLAVOR == "premium"){
//
//        }else{
//
//        }
//
//        val a = BuildConfig.URL
//
//    }

}