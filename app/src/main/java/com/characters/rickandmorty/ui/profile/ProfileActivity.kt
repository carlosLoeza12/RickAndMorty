package com.characters.rickandmorty.ui.profile

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import coil.load
import com.characters.rickandmorty.R
import com.characters.rickandmorty.application.UserPreferences
import com.characters.rickandmorty.core.initialize
import com.characters.rickandmorty.data.local.SigInType
import com.characters.rickandmorty.data.local.User
import com.characters.rickandmorty.databinding.ActivityProfileBinding
import com.characters.rickandmorty.databinding.PopUpInformationBinding
import com.characters.rickandmorty.databinding.PopUpInformationYesNoBinding
import com.characters.rickandmorty.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var appInformationDialog: Dialog
    private lateinit var popUpInformationBinding : PopUpInformationBinding
    private lateinit var popUpQuitAppBinding: PopUpInformationYesNoBinding
    private lateinit var quitAppDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        //Status bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.text_profile_title)
        setProfileData()

        //Dialog information
        popUpInformationBinding = PopUpInformationBinding.inflate(LayoutInflater.from(this))
        appInformationDialog = Dialog(this)
        appInformationDialog.initialize(popUpInformationBinding.root, true)

        //Dialog quit app
        popUpQuitAppBinding = PopUpInformationYesNoBinding.inflate(LayoutInflater.from(this))
        quitAppDialog = Dialog(this)
        quitAppDialog.initialize(popUpQuitAppBinding.root, true)


        binding.txtExit.setOnClickListener {
            showQuitAppDialog()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setProfileData(){
       val user = UserPreferences.get<User>(UserPreferences.USER_KEY)
        if(user != null){
            binding.imgProfile.load(user.urlProfile)
            binding.txtName.text = getString(R.string.format_location_name, user.name ?: "")
            binding.txtLastName.text = getString(R.string.format_last_name, user.lastName ?: "")
            binding.txtEmail.text = getString(R.string.format_email,  user.email ?: "")

            if(user.sigInType == SigInType.GOOGLE){
                binding.imgSignInType.setImageResource(R.drawable.gmail)
            }else{
                binding.imgSignInType.setImageResource(R.drawable.facebook)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Menu in action bar
        menuInflater.inflate(R.menu.info_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navInformation -> {
                appInformationDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showQuitAppDialog(){
        popUpQuitAppBinding.txtInformation.text = getString(R.string.information_quit_app)

        popUpQuitAppBinding.btnYes.setOnClickListener {
            quitAppDialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            UserPreferences.wipeData()
            startActivity(intent)
        }

        popUpQuitAppBinding.btnNo.setOnClickListener {
            quitAppDialog.dismiss()
        }

        quitAppDialog.show()
    }
}