package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.toPokemonList
import com.petruccini.pokephone.domain.entities.PokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemotePokemonListDataSource @Inject constructor(
    private val pokemonListService: PokemonListService
) {

    suspend fun fetchPokemonList(offset: Int, limit: Int): Flow<PokemonList?> {
        val response = pokemonListService.getPokemons(offset, limit)
        return flowOf(response.body()?.toPokemonList())
    }
}