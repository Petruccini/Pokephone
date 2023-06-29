package com.petruccini.pokephone.domain.use_cases

import java.util.Locale
import javax.inject.Inject

class FormatPokemonNameUseCase @Inject constructor() {

    operator fun invoke(pokemonName: String) =
        pokemonName.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
}