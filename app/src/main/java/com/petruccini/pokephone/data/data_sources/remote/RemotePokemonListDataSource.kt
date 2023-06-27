package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.toPokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemotePokemonListDataSource @Inject constructor(
    private val pokemonListService: PokemonListService
) {

    suspend fun fetchPokemonList(offset: Int, limit: Int) = flow {
        val response = pokemonListService.getPokemons(offset, limit)
        emit(response.body()?.toPokemonList())
    }.flowOn(Dispatchers.IO)
}