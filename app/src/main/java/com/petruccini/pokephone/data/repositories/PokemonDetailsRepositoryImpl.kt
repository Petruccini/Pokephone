package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonDetailsDataSource
import com.petruccini.pokephone.domain.use_cases.pokemon_details.PokemonDetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(
    private val remotePokemonDetailsDataSource: RemotePokemonDetailsDataSource,
): PokemonDetailsRepository {

    override suspend fun fetchPokemonDetails(pokemonName: String) = flow {
        emit(remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName))
    }
}