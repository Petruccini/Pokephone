package com.petruccini.pokephone.domain.use_cases.pokemon_details

import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonDetailsRepository
) {

    suspend operator fun invoke(pokemonName: String) =
        repository.fetchPokemonDetails(pokemonName)
}