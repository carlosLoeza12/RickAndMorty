package com.characters.rickandmorty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.ActivityNavigator
import com.characters.rickandmorty.ui.charactersaved.CharacterSavedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navSaved ->{
                val activityNavigator = ActivityNavigator( this)
                activityNavigator.navigate(
                    activityNavigator.createDestination().setIntent(
                        Intent(
                            this,
                            CharacterSavedActivity::class.java
                        )
                    ), null, null, null
                )
            }
        }
        return super.onOptionsItemSelected(item)
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