package com.petruccini.pokephone.domain.use_cases.pokemon_details

import com.petruccini.pokephone.config.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonDetailsRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(pokemonName: String) =
        repository.fetchPokemonDetails(pokemonName).flowOn(dispatcher)
}