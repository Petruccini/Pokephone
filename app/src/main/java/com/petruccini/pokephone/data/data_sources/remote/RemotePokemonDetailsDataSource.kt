package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_details.model.toPokemonDetails
import javax.inject.Inject

class RemotePokemonDetailsDataSource @Inject constructor(
    private val pokemonDetailsService: PokemonDetailsService
) {

    suspend fun fetchPokemonDetails(pokemonName: String) =
        pokemonDetailsService.getPokemonDetails(pokemonName).body()?.toPokemonDetails()
}