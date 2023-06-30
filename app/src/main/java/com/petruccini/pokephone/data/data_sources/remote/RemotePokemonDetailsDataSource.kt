package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.config.IoDispatcher
import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_details.model.toPokemonDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePokemonDetailsDataSource @Inject constructor(
    private val pokemonDetailsService: PokemonDetailsService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchPokemonDetails(pokemonName: String) = withContext(dispatcher) {
        pokemonDetailsService.getPokemonDetails(pokemonName).body()?.toPokemonDetails()
    }
}