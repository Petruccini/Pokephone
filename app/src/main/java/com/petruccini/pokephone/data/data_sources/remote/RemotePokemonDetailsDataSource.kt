package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_details.model.toPokemonDetails
import com.petruccini.pokephone.domain.entities.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemotePokemonDetailsDataSource @Inject constructor(
    private val pokemonDetailsService: PokemonDetailsService
) {

    suspend fun fetchPokemonDetails(pokemonName: String): Flow<PokemonDetails?> {
        val response = pokemonDetailsService.getPokemonDetails(pokemonName)
        return flowOf(response.body()?.toPokemonDetails())
    }
}