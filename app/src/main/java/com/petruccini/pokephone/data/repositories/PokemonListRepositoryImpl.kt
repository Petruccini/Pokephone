package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.config.IoDispatcher
import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import com.petruccini.pokephone.domain.use_cases.pokemon_list.PokemonListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val remotePokemonListDataSource: RemotePokemonListDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PokemonListRepository {

    override suspend fun fetchPokemonList(offset: Int, limit: Int) = flow {
        emit(remotePokemonListDataSource.fetchPokemonList(offset, limit))
    }.flowOn(dispatcher)


}