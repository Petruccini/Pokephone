package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import javax.inject.Inject

class PokemonListRepository @Inject constructor(
    private val remotePokemonListDataSource: RemotePokemonListDataSource
) {

    suspend fun fetchPokemonList(offset: Int, limit: Int) =
        remotePokemonListDataSource.fetchPokemonList(offset, limit)

}