package com.characters.rickandmorty

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.core.validatePermission
import com.characters.rickandmorty.data.local.Permissions
import com.characters.rickandmorty.databinding.ActivityMainBinding
import com.characters.rickandmorty.databinding.PopUpInformationBinding
import com.characters.rickandmorty.databinding.PopUpInformationYesNoBinding
import com.characters.rickandmorty.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permisos Aceptados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se aceptaron los permisos", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appInformationDialog: Dialog
    private lateinit var quitAppDialog: Dialog
    private lateinit var popUpInformationBinding : PopUpInformationBinding
    private lateinit var popUpQuitAppBinding: PopUpInformationYesNoBinding
    private val activityNavigator: ActivityNavigator by lazy {
        ActivityNavigator(this)
    }

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
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navProfile -> {
                //appInformationDialog.show()
                activityNavigator.navigate(
                    activityNavigator.createDestination().setIntent(
                        Intent(this, ProfileActivity::class.java)
                    ), null, null, null
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //for click on back
        val isFragmentHome =  navController.currentDestination?.id ==  navController.graph[R.id.characterFragment].id
        if(keyCode == KeyEvent.KEYCODE_BACK && isFragmentHome){
            // (return false) blocks the on back action and show the dialog
            showQuitAppDialog()
            return false
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

        //Permission for notification
        askNotificationPermission()

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

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val result = validatePermission(this)
            if(result == Permissions.REFUSED || shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)){
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

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