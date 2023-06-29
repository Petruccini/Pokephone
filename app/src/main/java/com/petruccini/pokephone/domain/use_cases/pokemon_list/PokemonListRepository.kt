package com.petruccini.pokephone.domain.use_cases.pokemon_list

import com.petruccini.pokephone.domain.entities.PokemonList
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {

    suspend fun fetchPokemonList(offset: Int, limit: Int): Flow<PokemonList?>
}