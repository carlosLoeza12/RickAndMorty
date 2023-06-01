package com.characters.rickandmorty.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object UserPreferences {

    //Shared Preference field used to save and retrieve JSON string
    lateinit var preferences: SharedPreferences
    //Name of Shared Preference file
    private const val USER_PREFERENCES = "USER_PREFERENCES"
    const val USER_KEY: String = "userData"
    private const val USER_SESSION: String = "userSession"

    // Call this first before retrieving or saving object.
    fun with(application: Application){
        preferences = application.getSharedPreferences(
            USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    fun <T>put(myObject: T, key: String){
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(myObject)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun saveSession(session: Boolean){
        preferences.edit().putBoolean(USER_SESSION, session).apply()
    }

    fun getSession() = preferences.getBoolean(USER_SESSION,false)

    fun wipeData(){
        preferences.edit().clear().apply()
    }
}