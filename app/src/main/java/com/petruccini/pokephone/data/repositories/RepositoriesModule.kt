package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.domain.use_cases.pokemon_details.PokemonDetailsRepository
import com.petruccini.pokephone.domain.use_cases.pokemon_list.PokemonListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    fun providePokemonDetailsRepository(
        implementation: PokemonDetailsRepositoryImpl
    ): PokemonDetailsRepository = implementation

    @Provides
    fun providePokemonListRepository(
        implementation: PokemonListRepositoryImpl
    ): PokemonListRepository = implementation
}