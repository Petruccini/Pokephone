package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.config.IoDispatcher
import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.toPokemonList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePokemonListDataSource @Inject constructor(
    private val pokemonListService: PokemonListService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchPokemonList(offset: Int, limit: Int) = withContext(dispatcher) {
        pokemonListService.getPokemons(offset, limit).body()?.toPokemonList()
    }
}