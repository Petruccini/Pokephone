package com.petruccini.pokephone.domain.use_cases

import com.petruccini.pokephone.data.repositories.PokemonListRepository
import javax.inject.Inject

const val POKEMON_LIST_LIMIT = 40

class GetPokemonListUseCase @Inject constructor(
    private val pokemonListRepository: PokemonListRepository
){

    suspend operator fun invoke(page: Int) =
        pokemonListRepository.fetchPokemonList(page * POKEMON_LIST_LIMIT, POKEMON_LIST_LIMIT)
}