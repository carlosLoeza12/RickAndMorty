package com.characters.rickandmorty.data.local

data class User(val id: String? = "",
                val name: String? = "",
                val lastName: String? = "",
                val email: String? = "",
                val urlProfile: String? = "",
                val sigInType: SigInType)

enum class SigInType{
    GOOGLE,
    FACEBOOK
}
