package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_details.model.toPokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemotePokemonDetailsDataSource @Inject constructor(
    private val pokemonDetailsService: PokemonDetailsService
) {

    suspend fun fetchPokemonDetails(pokemonName: String) = flow {
        val response = pokemonDetailsService.getPokemonDetails(pokemonName)
        emit(response.body()?.toPokemonDetails())
    }.flowOn(Dispatchers.IO)
}