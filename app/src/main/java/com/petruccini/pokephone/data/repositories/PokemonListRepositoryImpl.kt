package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import com.petruccini.pokephone.domain.use_cases.pokemon_list.PokemonListRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val remotePokemonListDataSource: RemotePokemonListDataSource
): PokemonListRepository {

    override suspend fun fetchPokemonList(offset: Int, limit: Int) = flow {
        emit(remotePokemonListDataSource.fetchPokemonList(offset, limit))
    }
}