package com.characters.rickandmorty.di

import com.characters.rickandmorty.repository.CharacterRepository
import com.characters.rickandmorty.repository.CharacterRepositoryImpl
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