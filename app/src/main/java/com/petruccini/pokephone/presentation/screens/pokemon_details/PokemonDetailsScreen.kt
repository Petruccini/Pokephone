package com.petruccini.pokephone.presentation.screens.pokemon_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonDetailsScreen(
    pokemonName: String,

) {
    Text(text = "PokemonDetailsScreen: $pokemonName")
}