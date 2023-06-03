package com.characters.rickandmorty.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characters.rickandmorty.R
import com.characters.rickandmorty.application.UserPreferences
import com.characters.rickandmorty.data.local.SigInType
import com.characters.rickandmorty.data.local.User
import com.characters.rickandmorty.ui.login.LoginActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    withContext(Dispatchers.Main){
                        saveUserData(
                            User(
                                account.id,
                                account.givenName,
                                account.familyName,
                                account.email,
                                account.photoUrl.toString(),
                                SigInType.GOOGLE
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    private fun saveUserData(userData: User) {
        UserPreferences.put(userData, UserPreferences.USER_KEY)
        UserPreferences.saveSession(true)
        _isSavedUser.value = true
    }

    fun doLoginFacebook(callbackManager: CallbackManager, activity: LoginActivity) {
        viewModelScope.launch {
           // doRequestFacebook(token)
            val result = withContext(Dispatchers.IO){getDataFacebook(callbackManager, activity) }

            when(result){
                is User ->{
                    saveUserData(result)
                }
                else ->{
                    println("error facebook login")
                }
            }
        }
    }

    private suspend fun getDataFacebook(callbackManager: CallbackManager, activity: LoginActivity): Any =

        suspendCoroutine {
            LoginManager.getInstance().logInWithReadPermissions(activity, listOf("email", "public_profile"))
            val loginManager = LoginManager.getInstance()
            loginManager.logOut()

            loginManager.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        //Graph API to access the data of user's facebook account
                        val request = GraphRequest.newMeRequest(result.accessToken) { fbObject, _ ->
                            try {
                                val id = result.accessToken.userId
                                val firstName = fbObject?.getString("first_name")
                                val lastName = fbObject?.getString("last_name")
                                val email = fbObject?.getString("email")
                                val urlProfile = fbObject?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
                                //println("$id $firstName, $lastName, $email, $urlPhoto")
                                it.resume(User(id, firstName, lastName, email, urlProfile, SigInType.FACEBOOK))
                            } catch (e: JSONException) {
                                it.resume(e)
                            }
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id, first_name, last_name, email, picture.type(large)")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        it.resume("cancel")
                        loginManager.logOut()
                    }

                    override fun onError(error: FacebookException) {
                        it.resume(error)
                        loginManager.logOut()
                    }
                })
        }
}