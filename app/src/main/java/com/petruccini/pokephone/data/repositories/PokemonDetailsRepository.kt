package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonDetailsDataSource
import javax.inject.Inject

class PokemonDetailsRepository @Inject constructor(
    private val remotePokemonDetailsDataSource: RemotePokemonDetailsDataSource
) {

    suspend fun fetchPokemonDetails(pokemonName: String) =
        remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName)
}