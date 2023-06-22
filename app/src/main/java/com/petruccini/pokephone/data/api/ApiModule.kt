package com.petruccini.pokephone.data.api

import com.petruccini.pokephone.data.api.pokemon_list.PokemonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providePokemonListService() = APIFactory.createService(PokemonListService::class.java)
}