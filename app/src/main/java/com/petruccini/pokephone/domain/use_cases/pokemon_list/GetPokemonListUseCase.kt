package com.petruccini.pokephone.domain.use_cases.pokemon_list

import javax.inject.Inject

const val POKEMON_LIST_LIMIT = 20

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonListRepository
){

    suspend operator fun invoke(page: Int) =
        repository.fetchPokemonList(page * POKEMON_LIST_LIMIT, POKEMON_LIST_LIMIT)
}