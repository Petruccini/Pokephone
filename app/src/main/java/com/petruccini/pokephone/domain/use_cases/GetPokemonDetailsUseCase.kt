package com.petruccini.pokephone.domain.use_cases

import com.petruccini.pokephone.data.repositories.PokemonDetailsRepository
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val pokemonDetailsRepository: PokemonDetailsRepository
) {

    suspend operator fun invoke(pokemonName: String) =
        pokemonDetailsRepository.fetchPokemonDetails(pokemonName)
}