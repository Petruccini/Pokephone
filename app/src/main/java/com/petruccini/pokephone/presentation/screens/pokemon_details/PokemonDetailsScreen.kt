package com.petruccini.pokephone.presentation.screens.pokemon_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware
import com.petruccini.pokephone.presentation.shared_components.ShowProgressBar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemonName: String
) {

    val capitalizedPokemonName = pokemonName.capitalizeFirstChar()

    val pokemonDetailsState = viewModel.pokemonDetailsStateFlow.collectAsStateLifecycleAware()
    val isLoading = viewModel.loadingPokemonDetailsStateFlow.collectAsStateLifecycleAware()

    if (isLoading.value) { ShowProgressBar() }

    LaunchedEffect(pokemonName) {
        viewModel.getPokemonDetails(pokemonName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$capitalizedPokemonName Details") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Height: ${pokemonDetailsState.value?.height}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = "Weight: ${pokemonDetailsState.value?.weight}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Text(
                    text = "Order: ${pokemonDetailsState.value?.order}",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        if (isLoading.value) {
            ShowProgressBar()
        }
    }

}

fun String.capitalizeFirstChar() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
}