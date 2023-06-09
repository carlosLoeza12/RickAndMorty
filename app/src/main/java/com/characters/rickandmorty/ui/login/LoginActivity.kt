package com.characters.rickandmorty.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.characters.rickandmorty.MainActivity
import com.characters.rickandmorty.databinding.ActivityLoginBinding
import com.characters.rickandmorty.presentation.CharacterLogInViewModel
import com.facebook.CallbackManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<CharacterLogInViewModel>()
    private lateinit var binding: ActivityLoginBinding
    private val callbackManager: CallbackManager by lazy {CallbackManager.Factory.create()}

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                if(activityResult.data != null){
                    //get data for result
                    viewModel.getDataForGmail(activityResult.data!!)
                    binding.progressBar.visibility = View.VISIBLE
                }
            } else {
                binding.btnGmail.isEnabled = true
                Log.e("error", "Its cancel")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initElements()
    }

    private fun initElements(){
        binding.btnGmail.setOnClickListener {
            it.isEnabled = false
            val sigInIntent = viewModel.getSigInIntent(this)
            loginLauncher.launch(sigInIntent)
        }

        viewModel.isSavedUser.observe(this){ isSaved ->
            binding.progressBar.visibility = View.INVISIBLE
            if(isSaved){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }else{
                binding.btnGmail.isEnabled = true
                //binding.btnFacebook.isEnabled = true
            }
        }


        binding.btnFacebook.setOnClickListener {
            //viewModel.doLoginFacebook(callbackManager, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}