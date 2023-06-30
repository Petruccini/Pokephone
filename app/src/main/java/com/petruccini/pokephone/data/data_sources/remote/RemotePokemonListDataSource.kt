package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.toPokemonList
import javax.inject.Inject

class RemotePokemonListDataSource @Inject constructor(
    private val pokemonListService: PokemonListService
) {

    suspend fun fetchPokemonList(offset: Int, limit: Int) =
        pokemonListService.getPokemons(offset, limit).body()?.toPokemonList()
}