package com.characters.rickandmorty.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.characters.rickandmorty.MainActivity
import com.characters.rickandmorty.application.UserPreferences
import com.characters.rickandmorty.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       timerSplash()
    }

    private fun timerSplash() {
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = if(UserPreferences.getSession()){
                    Intent(this@SplashActivity, MainActivity::class.java)
                }else{
                    Intent(this@SplashActivity, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }
}