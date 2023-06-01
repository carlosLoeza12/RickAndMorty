package com.characters.rickandmorty.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characters.rickandmorty.R
import com.characters.rickandmorty.application.UserPreferences
import com.characters.rickandmorty.data.local.SigInType
import com.characters.rickandmorty.data.local.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterLogInViewModel @Inject constructor(): ViewModel() {

    private val _isSavedUser = MutableLiveData<Boolean>()
    val isSavedUser: LiveData<Boolean> = _isSavedUser

    fun getSigInIntent(context: Context): Intent{
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleClient: GoogleSignInClient = GoogleSignIn.getClient(context, googleConf)
        googleClient.signOut()

        return googleClient.signInIntent
    }

    fun getDataForGmail(data: Intent) {
        try {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                saveDataForGmail(account)
            }
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

    private fun saveDataForGmail(account: GoogleSignInAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                account.id,
                account.givenName,
                account.familyName,
                account.email,
                account.photoUrl.toString(),
                SigInType.GOOGLE
            )
            UserPreferences.put(user, UserPreferences.USER_KEY)
            UserPreferences.saveSession(true)
            _isSavedUser.postValue( true)
        }
    }
}