package com.example.rickandmorty.di

import com.example.rickandmorty.repository.RickAndMortyRepository
import com.example.rickandmorty.repository.RickAndMortyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindRepoImpl(rickAndMortyRepositoryImpl: RickAndMortyRepositoryImpl): RickAndMortyRepository
}