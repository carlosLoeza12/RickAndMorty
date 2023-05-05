package com.characters.rickandmorty

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.databinding.ActivityMainBinding
import com.characters.rickandmorty.databinding.PopUpInformationBinding
import com.characters.rickandmorty.databinding.PopUpInformationYesNoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appInformationDialog: Dialog
    private lateinit var quitAppDialog: Dialog
    private lateinit var popUpInformationBinding : PopUpInformationBinding
    private lateinit var popUpQuitAppBinding: PopUpInformationYesNoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
    }

    override fun onSupportNavigateUp(): Boolean {
        //For the navigation in fragments
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Menu in action bar
        menuInflater.inflate(R.menu.info_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navInfo -> {
                appInformationDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //for click on back
        val isFragmentHome =  navController.currentDestination?.id ==  navController.graph[R.id.characterFragment].id
        if(keyCode == KeyEvent.KEYCODE_BACK && isFragmentHome){
            showQuitAppDialog()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initComponents(){

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController =  navHostFragment.navController

        //set tup bottom navigation with nav controller
        binding.bottomNavigation.setupWithNavController(navController)
        //set up action bar with nav controller
        NavigationUI.setupActionBarWithNavController(this, navController)

        //Dialog information
        popUpInformationBinding = PopUpInformationBinding.inflate(LayoutInflater.from(this))
        appInformationDialog = Dialog(this)
        appInformationDialog.initialize(popUpInformationBinding.root, true)

        //Dialog quit app
        popUpQuitAppBinding = PopUpInformationYesNoBinding.inflate(LayoutInflater.from(this))
        quitAppDialog = Dialog(this)
        quitAppDialog.initialize(popUpQuitAppBinding.root, true)
    }

    private fun showQuitAppDialog(){
        popUpQuitAppBinding.txtInformation.text = getString(R.string.information_quit_app)

        popUpQuitAppBinding.btnYes.setOnClickListener {
           finishAffinity()
        }

        popUpQuitAppBinding.btnNo.setOnClickListener {
            quitAppDialog.dismiss()
        }

        quitAppDialog.show()
    }

    //code for navigation component
//    val activityNavigator = ActivityNavigator( this)
//    activityNavigator.navigate(
//    activityNavigator.createDestination().setIntent(
//    Intent(
//    this,
//    CharacterSavedActivity::class.java
//    )
//    ), null, null, null
//    )


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