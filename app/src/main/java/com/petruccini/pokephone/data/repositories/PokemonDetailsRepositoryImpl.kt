package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.config.IoDispatcher
import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonDetailsDataSource
import com.petruccini.pokephone.domain.use_cases.pokemon_details.PokemonDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(
    private val remotePokemonDetailsDataSource: RemotePokemonDetailsDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PokemonDetailsRepository {

    override suspend fun fetchPokemonDetails(pokemonName: String) = flow {
        emit(remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName))
    }.flowOn(dispatcher)
}