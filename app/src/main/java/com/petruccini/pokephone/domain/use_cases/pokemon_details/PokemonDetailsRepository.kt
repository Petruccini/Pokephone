package com.petruccini.pokephone.domain.use_cases.pokemon_details

import com.petruccini.pokephone.domain.entities.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {

    suspend fun fetchPokemonDetails(pokemonName: String): Flow<PokemonDetails?>
}