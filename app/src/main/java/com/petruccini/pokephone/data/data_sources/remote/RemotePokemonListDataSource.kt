package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.PokemonServices
import com.petruccini.pokephone.data.api.model.toPokemonList
import com.petruccini.pokephone.domain.entities.PokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemotePokemonListDataSource @Inject constructor(
    private val pokemonServices: PokemonServices
) {


    suspend fun fetchPokemonList(offset: Int, limit: Int): Flow<PokemonList?> {
        val response = pokemonServices.getPokemons(offset, limit)
        return flowOf(response.body()?.toPokemonList())
    }
}