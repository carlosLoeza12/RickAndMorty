package com.example.rickandmorty.di

import com.example.rickandmorty.repository.CharacterRepository
import com.example.rickandmorty.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindRepoImpl(rickAndMortyRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}