package com.petruccini.pokephone.data.api

import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {

    @Provides
    fun providePokemonListService() = APIFactory.createService(PokemonListService::class.java)

    @Provides
    fun providePokemonDetailsService() = APIFactory.createService(PokemonDetailsService::class.java)
}