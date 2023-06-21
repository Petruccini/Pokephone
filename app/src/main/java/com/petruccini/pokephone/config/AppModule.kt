package com.petruccini.pokephone.config

import com.petruccini.pokephone.data.api.APIFactory
import com.petruccini.pokephone.data.api.PokemonServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthServices(): PokemonServices = APIFactory.pokemonServices
}