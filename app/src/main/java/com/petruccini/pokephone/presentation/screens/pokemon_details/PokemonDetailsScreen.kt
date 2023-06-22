package com.petruccini.pokephone.presentation.screens.pokemon_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware

@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemonName: String
) {

    val pokemonDetailsState = viewModel.pokemonDetailsStateFlow.collectAsStateLifecycleAware()
    val isLoading = viewModel.loadingPokemonDetailsStateFlow.collectAsStateLifecycleAware()

    if (isLoading.value) { ShowProgressBar() }

    LaunchedEffect(pokemonName) {
        viewModel.getPokemonDetails(pokemonName)
    }

    Column {
        Text(text = "Pokemon Name: $pokemonName")
        Row {
           Text(text = "Height: ${pokemonDetailsState.value?.height}")
           Text(text = "Weight: ${pokemonDetailsState.value?.weight}")
        }
        Text(text = "Order: ${pokemonDetailsState.value?.order}")
    }

}

@Composable
fun ShowProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}