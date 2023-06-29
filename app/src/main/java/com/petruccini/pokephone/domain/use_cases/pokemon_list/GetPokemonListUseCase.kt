package com.petruccini.pokephone.domain.use_cases.pokemon_list

import com.petruccini.pokephone.config.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val POKEMON_LIST_LIMIT = 20

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonListRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(page: Int) =
        repository.fetchPokemonList(page * POKEMON_LIST_LIMIT, POKEMON_LIST_LIMIT)
            .flowOn(dispatcher)
}